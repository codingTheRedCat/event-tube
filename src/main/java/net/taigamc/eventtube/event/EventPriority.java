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
 * Represents event priority.
 *
 * <p>EventPriority defines in which order handlers get events. Event priorities are handled sooner and later.
 * They can also be more influential an less influential. Weaker priorities are handled first, so they can be easily
 * overwritten, so they have less power to form the final event state. Stronger priorities are handled last, so they can
 * overwrite weaker ones. The sooner event is handled the less power it has. The later event is handled the more power
 * it has.</p>
 *
 * @since 1.0.0
 */
public enum EventPriority {

  /**
   * The least influential and the soonest handled priority.
   *
   * @since 1.0.0
   */
  EARLIER,
  /**
   * Handled sooner than {@link EventPriority#NORMAL} and later than {@link EventPriority#EARLIER}.
   *
   * @since 1.0.0
   */
  EARLY,
  /**
   * Handled sooner than {@link EventPriority#LATE} and later than {@link EventPriority#EARLY}.
   *
   * @since 1.0.0
   */
  NORMAL,
  /**
   * Handled sooner than {@link EventPriority#LATER} and later than {@link EventPriority#NORMAL}.
   *
   * @since 1.0.0
   */
  LATE,
  /**
   * Handled before {@link EventPriority#WATCHER} and after {@link EventPriority#LATE}. THe most influential of all priorities giving write access.
   *
   * @since 1.0.0
   */
  LATER,
  /**
   * Handled last. Can access event properties but not change them. Used to watch events without need to manipulate them.
   *
   * @since 1.0.0
   */
  WATCHER

}
