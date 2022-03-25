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

/**
 * Thrown when event handler occurs an exception.
 *
 * @since 1.0.0
 */
public class EventHandlerException extends Exception {

  /**
   * Wraps a throwable into an EventHandlerException.
   *
   * @param cause the throwable
   *
   * @since 1.0.0
   */
  public EventHandlerException(final Throwable cause) {
    super("Event handler has thrown an exception.", cause);
  }

}
