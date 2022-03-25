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

import java.util.function.Consumer;

/**
 * Class Explorer.
 *
 * @since 1.0.0
 */
public final class ClassExplorer {

  /**
   * Applies an action to this class or its superclasses and interfaces of them if particular class is subclass or
   * implements 'of' class.
   *
   * @param of          applied classes must implement/extend this class
   * @param inClassTree base class the root of class/interface tree
   * @param action      action to perform
   *
   * @since 1.0.0
   */
  public static void forEachSubclasses(final Class<?> of, final Class<?> inClassTree, final Consumer<Class<?>> action) {
    Class<?> superclass = inClassTree;
    while (superclass != null) {
      if (superclass.equals(of)) {
        return;
      } else if (ClassExplorer.forEachInterfaces(of, superclass, action)) {
        action.accept(superclass);
      }
      superclass = superclass.getSuperclass();
    }
  }

  private static boolean forEachInterfaces(final Class<?> parentClass, final Class<?> startPoint, final Consumer<Class<?>> action) {
    boolean subclass = false;
    for (int i = 0; i < startPoint.getInterfaces().length; i++) {
      final Class<?> clazz = startPoint.getInterfaces()[i];
      if (clazz.equals(parentClass)) {
        subclass = true;
      } else {
        subclass = forEachInterfaces(parentClass, clazz, action);
      }
    }
    if (subclass) {
      action.accept(startPoint);
    }
    return subclass;
  }

  private ClassExplorer() {

  }

}
