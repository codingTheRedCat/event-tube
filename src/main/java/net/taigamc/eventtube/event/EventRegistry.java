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
 * Stores event classes.
 *
 * @since 1.0.0
 */
public interface EventRegistry {

  /**
   * Registers an event class, so it can be handled.
   *
   * @param eventClass class to register
   *
   * @since 1.0.0
   */
  void registerEvent(Class<? extends Event> eventClass);

  /**
   * Removes an event class from the registry.
   *
   * @param eventClass class to remove
   *
   * @since 1.0.0
   */
  void unregisterEvent(Class<? extends Event> eventClass);

}
