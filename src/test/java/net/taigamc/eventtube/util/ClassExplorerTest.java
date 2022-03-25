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
package net.taigamc.eventtube.util;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import net.taigamc.eventtube.event.AbstractCancellableEvent;
import net.taigamc.eventtube.event.AbstractEvent;
import net.taigamc.eventtube.event.AbstractMutableEvent;
import net.taigamc.eventtube.event.CancellableEvent;
import net.taigamc.eventtube.event.Event;
import net.taigamc.eventtube.event.MutableEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassExplorerTest {

  @Test
  void forEachSubclasses() {
    assertEquals(Set.of(CancellableEvent.class, MutableEvent.class), this.makeClassSet(Event.class, CancellableEvent.class));
    assertEquals(Set.of(MutableEvent.class), this.makeClassSet(Event.class, MutableEvent.class));
    assertEquals(Set.of(), this.makeClassSet(Event.class, Event.class));
    assertEquals(Set.of(), this.makeClassSet(Event.class, Duration.class));
    assertEquals(Set.of(CancellableEvent.class, MutableEvent.class, AbstractEvent.class, AbstractMutableEvent.class, AbstractCancellableEvent.class), this.makeClassSet(Event.class, AbstractCancellableEvent.class));
    assertEquals(Set.of(MutableEvent.class, AbstractEvent.class, AbstractMutableEvent.class), this.makeClassSet(Event.class, AbstractMutableEvent.class));
    assertEquals(Set.of(AbstractEvent.class), this.makeClassSet(Event.class, AbstractEvent.class));
  }

  private Set<Class<?>> makeClassSet(final Class<?> of, final Class<?> tree) {
    final Set<Class<?>> set = new HashSet<>();
    ClassExplorer.forEachSubclasses(of, tree, set::add);
    return set;
  }

}
