/*
 * Difference.java
 * 
 * Copyright (c) 2013, Instituto Superior Técnico. All rights reserved.
 * 
 * This file is part of bennu-core.
 * 
 * bennu-core is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * bennu-core is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with bennu-core. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.bennu.core.grouplanguage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fenixedu.bennu.core.domain.groups.DifferenceGroup;
import org.fenixedu.bennu.core.domain.groups.Group;

class Difference extends GroupToken {
    private static final long serialVersionUID = -5432713796834101478L;

    private final List<GroupToken> children;

    public Difference(List<GroupToken> children) {
        this.children = children;
    }

    @Override
    public Group group() {
        Set<Group> groups = new HashSet<>();
        for (GroupToken groupToken : children) {
            groups.add(groupToken.group());
        }
        return DifferenceGroup.getInstance(groups);
    }
}
