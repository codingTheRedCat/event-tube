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
package net.taigamc.eventtube.handler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.taigamc.eventtube.event.Event;
import net.taigamc.eventtube.event.EventPriority;

/**
 * Handles specific type of events.
 *
 * @param <E> event type
 *
 * @since 1.0.0
 */
public interface EventHandler<E extends Event> extends Comparable<EventHandler<?>> {

  /**
   * Creates a new functional interface based event handler.
   *
   * <p>This event handler implementation ignores cancelled events.</p>
   * <p>The event priority is {@link EventPriority#NORMAL}.</p>
   *
   * @param clazz    class of handled events
   * @param consumer consumer that handles the event objects
   * @param <E>      event type
   * @return created handler
   *
   * @since 1.0.0
   */
  static <E extends Event> EventHandler<E> functional(final Class<E> clazz, final Consumer<E> consumer) {
    return new FunctionalEventHandlerImpl<>(EventPriority.NORMAL, true, clazz, consumer);
  }

  /**
   * Creates a new functional interface based event handler.
   *
   * <p>The event priority is {@link EventPriority#NORMAL}.</p>
   *
   * @param clazz           class of handled events
   * @param consumer        consumer that handles the event objects
   * @param ignoreCancelled does it ignore cancelled events?
   * @param <E>             event type
   * @return created handler
   *
   * @since 1.0.0
   */
  static <E extends Event> EventHandler<E> functional(final Class<E> clazz, final Consumer<E> consumer, final boolean ignoreCancelled) {
    return new FunctionalEventHandlerImpl<>(EventPriority.NORMAL, ignoreCancelled, clazz, consumer);
  }

  /**
   * Creates a new functional interface based event handler.
   *
   * <p>This event handler implementation ignores cancelled events.</p>
   *
   * @param clazz    class of handled events
   * @param consumer consumer that handles the event objects
   * @param priority event priority
   * @param <E>      event type
   * @return created handler
   *
   * @since 1.0.0
   */
  static <E extends Event> EventHandler<E> functional(final Class<E> clazz, final Consumer<E> consumer, final EventPriority priority) {
    return new FunctionalEventHandlerImpl<>(priority, true, clazz, consumer);
  }

  /**
   * Creates a new functional interface based event handler.
   *
   * @param clazz           class of handled events
   * @param consumer        consumer that handles the event objects
   * @param priority        event priority
   * @param ignoreCancelled does it ignore cancelled events?
   * @param <E>             event type
   * @return created handler
   *
   * @since 1.0.0
   */
  static <E extends Event> EventHandler<E> functional(final Class<E> clazz, final Consumer<E> consumer, final EventPriority priority, final boolean ignoreCancelled) {
    return new FunctionalEventHandlerImpl<>(priority, ignoreCancelled, clazz, consumer);
  }

  /**
   * Creates a new reflection-based event handler.
   *
   * @param handlerMethod method that handles events
   * @param handlerObject the handler object
   * @return created handler
   *
   * @since 1.0.0
   */
  static EventHandler<?> reflection(final Method handlerMethod, final Object handlerObject) {
    return ReflectionEventHandlerImpl.newImpl(handlerMethod, handlerObject);
  }

  /**
   * Creates a group of reflection-based handlers by scanning object's class for @EventHandler annotated method.
   *
   * @param handlerObject a collection containing created handlers
   * @return created handlers
   *
   * @since 1.0.0
   */
  static Collection<EventHandler<?>> reflection(final Object handlerObject) {
    return Arrays.stream(handlerObject.getClass().getDeclaredMethods()).filter(m -> m.getAnnotation(net.taigamc.eventtube.annotations.EventHandler.class) != null).map(m -> EventHandler.reflection(m, handlerObject)).collect(Collectors.toSet());
  }

  /**
   * Handles an event.
   *
   * @param event event to handle
   *
   * @since 1.0.0
   */
  void handle(E event) throws EventHandlerException;

  /**
   * Gets the priority of this handler.
   *
   * <p>The priority determinate when and whether an incoming event is handled.</p>
   *
   * @return handler priority
   *
   * @since 1.0.0
   */
  EventPriority priority();

  /**
   * Gets the policy of handling cancelled events. Whether event is ignored or handled.
   *
   * @return false if it handles cancelled event, else true.
   *
   * @since 1.0.0
   */
  boolean ignoresCancelled();

  /**
   * Gets the class of handled events.
   *
   * @return event class
   *
   * @since 1.0.0
   */
  Class<?> eventClass();

  /**
   * Compares event handlers by priority.
   *
   * @param o handler to compare
   * @return 0 if priorities are equal, negative when this object's priority is smaller, positive otherwise.
   *
   * @since 1.0.0
   */
  @Override
  default int compareTo(EventHandler<?> o) {
    return Integer.compareUnsigned(o.priority().ordinal(), this.priority().ordinal());
  }

}
