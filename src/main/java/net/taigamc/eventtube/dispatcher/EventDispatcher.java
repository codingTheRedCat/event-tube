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

import net.taigamc.eventtube.event.Event;

/**
 * Broadcasts an event among event handlers.
 *
 * @since 1.0.0
 */
public interface EventDispatcher {

  /**
   * Dispatches an event.
   *
   * @param event      event
   * @param eventClass class of event (can be a superclass or implemented interface)
   * @throws IllegalArgumentException when event is not an instance of eventClass
   * @throws IllegalStateException    when eventClass is not registered
   *
   * @since 1.0.0
   */
  void dispatch(Event event, final Class<?> eventClass);

  /**
   * Dispatches an event.
   *
   * @param event event
   * @throws IllegalStateException when event's class is not registered
   *
   * @since 1.0.0
   */
  default void dispatch(final Event event) {
    this.dispatch(event, event.getClass());
  }

}
