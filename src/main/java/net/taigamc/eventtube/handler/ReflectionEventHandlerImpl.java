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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.taigamc.eventtube.annotations.EventHandler;
import net.taigamc.eventtube.annotations.HandleCancelled;
import net.taigamc.eventtube.annotations.Priority;
import net.taigamc.eventtube.event.Event;
import net.taigamc.eventtube.event.EventPriority;

class ReflectionEventHandlerImpl extends AbstractEventHandler<Event> {

  static net.taigamc.eventtube.handler.EventHandler<?> newImpl(final Method method, final Object handler) {
    ReflectionEventHandlerImpl.checkMethod(method);
    return new ReflectionEventHandlerImpl(method, ReflectionEventHandlerImpl.priority(method), ReflectionEventHandlerImpl.ignoresCancelled(method), method.getParameters()[0].getType(), handler);
  }

  private final Method method;

  private final Object handler;

  ReflectionEventHandlerImpl(final Method method, final EventPriority priority, final boolean ignoresCancelled, final Class<?> clazz, final Object handler) {
    super(priority, ignoresCancelled, clazz);
    method.setAccessible(true);
    this.method = method;
    this.handler = handler;
  }

  private static void checkMethod(final Method method) {
    if (method.getAnnotation(EventHandler.class) == null) throw new IllegalArgumentException("The method must have @EventHandler annotation.");
    if (method.getParameterCount() != 1) throw new IllegalArgumentException("The method must have only one parameter.");
    if (!Event.class.isAssignableFrom(method.getParameters()[0].getType())) throw new IllegalArgumentException("The method parameter must implement Event.");
  }

  private static EventPriority priority(final Method method) {
    if (method.getAnnotation(Priority.class) != null) {
      return method.getAnnotation(Priority.class).value();
    } else {
      return EventPriority.NORMAL;
    }
  }

  private static boolean ignoresCancelled(final Method method) {
    return method.getAnnotation(HandleCancelled.class) == null;
  }

  @Override
  public void handle(final Event event) throws EventHandlerException {
    if (this.shouldHandle(event)) {
      try {
        this.method.invoke(this.handler, event);
      } catch (final IllegalAccessException ignored) {
      } catch (final InvocationTargetException e) {
        throw new EventHandlerException(e.getTargetException());
      }
    }
  }

}
