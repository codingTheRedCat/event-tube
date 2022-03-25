/*
 * This file is part of event tube, licensed under GNU General Public License v3.0
 * Copyright (C)2022 TaigaMC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.taigamc.eventtube.dispatcher;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import net.taigamc.eventtube.event.AbstractMutableEvent;
import net.taigamc.eventtube.event.Event;
import net.taigamc.eventtube.event.EventPriority;
import net.taigamc.eventtube.handler.EventHandler;
import net.taigamc.eventtube.handler.EventHandlerException;
import net.taigamc.eventtube.util.ClassExplorer;

class SimpleEventDispatcherImpl implements SimpleEventDispatcher {

  private final Map<Class<?>, Map<EventPriority, List<EventHandler<?>>>> handlerRegistry;

  private final Map<Class<?>, Collection<Class<?>>> eventRegistry;

  private final BiConsumer<EventHandler<?>, EventHandlerException> errorHandler;

  protected SimpleEventDispatcherImpl(final BiConsumer<EventHandler<?>, EventHandlerException> errorHandler) {
    this.errorHandler = errorHandler;
    this.handlerRegistry = new HashMap<>();
    this.eventRegistry = new HashMap<>();
  }

  @Override
  public void dispatch(final Event event, final Class<?> eventClass) {
    this.mobilizeHandlers(eventClass).forEach(handler -> {
      try {
        if (event instanceof AbstractMutableEvent && handler.priority().equals(EventPriority.WATCHER) && !((AbstractMutableEvent) event).writeLocked()) ((AbstractMutableEvent) event).lock();
        SimpleEventDispatcherImpl.dispatchEvent(event, handler);
      } catch (final EventHandlerException e) {
        this.errorHandler.accept(handler, e);
      }
    });
  }

  @SuppressWarnings("unchecked")
  private static <E extends Event> void dispatchEvent(final Event event, final EventHandler<E> handler) throws EventHandlerException {
    handler.handle((E) event);
  }

  @Override
  public void registerHandler(final EventHandler<?> handler) {
    if (!this.handlerRegistry.containsKey(handler.eventClass())) this.handlerRegistry.put(handler.eventClass(), new EnumMap<>(EventPriority.class));
    final Map<EventPriority, List<EventHandler<?>>> map = this.handlerRegistry.get(handler.eventClass());
    if (!map.containsKey(handler.priority())) this.handlerRegistry.get(handler.eventClass()).put(handler.priority(), new LinkedList<>());
    map.get(handler.priority()).add(handler);
  }

  @Override
  public void unregisterHandler(final EventHandler<?> handler) {
    if (this.handlerRegistry.containsKey(handler.eventClass())) {
      final Map<EventPriority, List<EventHandler<?>>> map = this.handlerRegistry.get(handler.eventClass());
      if (map.containsKey(handler.priority())) {
        map.get(handler.priority()).remove(handler);
      }
    }
  }

  @Override
  public List<EventHandler<?>> mobilizeHandlers(final Class<?> eventClass) {
    final Map<EventPriority, List<EventHandler<?>>> handlerMap = new EnumMap<>(EventPriority.class);
    this.availableClasses(eventClass).forEach(clazz -> {
      if (!this.handlerRegistry.containsKey(clazz)) return;
      handlerMap.putAll(this.handlerRegistry.get(clazz));
    });
    final List<EventHandler<?>> result = new LinkedList<>();
    for (final List<EventHandler<?>> handlerSet : handlerMap.values()) result.addAll(handlerSet);
    return Collections.unmodifiableList(result);
  }

  private Collection<Class<?>> availableClasses(final Class<?> eventClass) {
    if (!this.eventRegistry.containsKey(eventClass)) throw new IllegalStateException(MessageFormat.format("Event class {0} have not been registered yet.", eventClass.getName()));
    return this.eventRegistry.get(eventClass);
  }

  @Override
  public void registerEvent(final Class<? extends Event> eventClass) {
    final List<Class<?>> correlated = new ArrayList<>();
    ClassExplorer.forEachSubclasses(Event.class, eventClass, correlated::add);
    this.eventRegistry.put(eventClass, correlated);
  }

  @Override
  public void unregisterEvent(final Class<? extends Event> eventClass) {
    this.eventRegistry.remove(eventClass);
  }
}
