/*
 * #%L
 * fixture
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package io.fixture.security

import io.fixture.domain.Group
import io.fixture.repository.GroupRepository
import io.fixture.repository.UserRepository
import java.util.LinkedList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.provisioning.GroupManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

[Component(value = "groupManager")]
class GroupManagerImpl [Autowired] (
        val groupRepository: GroupRepository,
        val userRepository: UserRepository
): GroupManager {

    [Transactional(readOnly = true)]
    override fun findAllGroups(): List<String> = groupRepository.findAllNames()

    [Transactional(readOnly = true)]
    override fun findUsersInGroup(groupName: String): List<String> = groupRepository.findAllUsernames(groupName)

    [Transactional]
    public override fun createGroup(groupName: String, grantedAuthorities: List<GrantedAuthority>) {

        val authorities: MutableSet<String> = hashSetOf()
        authorities.addAll(
                grantedAuthorities.map { it.getAuthority() }
        )

        val group = Group()
        group.name = groupName
        group.authorities = authorities

        groupRepository.save(group)
    }

    [Transactional]
    override fun deleteGroup(groupName: String) {

        groupRepository.delete(groupName)
    }

    [Transactional]
    override fun renameGroup(oldName: String, newName: String) {

        val group = groupRepository.findOne(oldName)

        if (group != null) {
            group.name = newName
            groupRepository.save(group)
        }
    }

    [Transactional]
    override fun addUserToGroup(username: String, groupName: String) {

        val group = groupRepository.findOne(groupName)
        val user = userRepository.findOne(username)

        if (group != null && user != null) {
            group.users.add(user)
            groupRepository.save(group)
        }
    }

    [Transactional]
    override fun removeUserFromGroup(username: String, groupName: String) {

        val group = groupRepository.findOne(groupName)
        val user = userRepository.findOne(username)

        if (group != null && user != null) {
            group.users.remove(user)
            groupRepository.save(group)
        }
    }

    [Transactional(readOnly = true)]
    override fun findGroupAuthorities(groupName: String): List<GrantedAuthority> {

        val group = groupRepository.findOne(groupName)

        if (group != null) {
            return group.authorities.map { SimpleGrantedAuthority(it) }
        }

        return LinkedList()
    }


    [Transactional]
    override fun addGroupAuthority(groupName: String, grantedAuthority: GrantedAuthority) {
        val group = groupRepository.findOne(groupName)

        if (group != null) {
            group.authorities.add(grantedAuthority.getAuthority())
            groupRepository.save(group)
        }
    }

    [Transactional]
    override fun removeGroupAuthority(groupName: String, grantedAuthority: GrantedAuthority) {

        val group = groupRepository.findOne(groupName)

        if (group != null) {
            group.authorities.remove(grantedAuthority.getAuthority())
            groupRepository.save(group)
        }
    }

}
