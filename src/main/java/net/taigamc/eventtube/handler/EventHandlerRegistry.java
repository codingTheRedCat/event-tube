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

import java.util.List;

/**
 * Stores event handlers.
 *
 * @since 1.0.0
 */
public interface EventHandlerRegistry {

  /**
   * Registers an event handler.
   *
   * @param handler event handler
   *
   * @since 1.0.0
   */
  void registerHandler(EventHandler<?> handler);

  /**
   * Fetches all reflection-based handlers from methods of handler object and registers them.
   *
   * <p>Fetching method {@link EventHandler#reflection(Object)}.</p>
   *
   * @param handlerObject holder of event handler methods
   *
   * @since 1.0.0
   */
  default void registerHandlers(final Object handlerObject) {
    EventHandler.reflection(handlerObject).forEach(this::registerHandler);
  }

  /**
   * Unregisters an event handler if registered previously.
   *
   * @param handler event handler
   *
   * @since 1.0.0
   */
  void unregisterHandler(EventHandler<?> handler);

  /**
   * Fetches all reflection-based handlers from methods of handler object and unregisters them.
   *
   * <p>Fetching method {@link EventHandler#reflection(Object)}.</p>
   *
   * @param handlerObject holder of event handler methods
   *
   * @since 1.0.0
   */
  default void unregisterHandlers(final Object handlerObject) {
    EventHandler.reflection(handlerObject).forEach(this::unregisterHandler);
  }

  /**
   * Prepares a list of handlers to handle an event of some class.
   *
   * <p>Retrieves all handlers that can handle event class of any subclass of it and sorts the results in order of its
   * {@link net.taigamc.eventtube.event.EventPriority}.</p>
   *
   * @param eventClass event class
   * @return list of handlers in order based on their priority
   *
   * @since 1.0.0
   */
  List<EventHandler<?>> mobilizeHandlers(Class<?> eventClass);

}
