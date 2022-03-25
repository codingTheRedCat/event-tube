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
package net.taigamc.eventtube.event;

/**
 * An event that can be cancelled.
 *
 * <p>When an event is cancelled it is not handled by the event handler.</p>
 *
 * @since 1.0.0
 */
public interface CancellableEvent extends MutableEvent {

  /**
   * Checks whether an event is cancelled.
   *
   * @param event event to check
   * @return result of a check
   *
   * @since 1.0.0
   */
  static boolean cancelled(final Event event) {
    return event instanceof CancellableEvent && ((CancellableEvent) event).cancelled();
  }

  /**
   * Gets whether this event is cancelled.
   *
   * @return true if it is, false otherwise.
   *
   * @since 1.0.0
   */
  boolean cancelled();

  /**
   * Sets the cancellation state of this event.
   *
   * @param value true to mark as cancelled, else false
   *
   * @since 1.0.0
   */
  void cancelled(boolean value);

}
