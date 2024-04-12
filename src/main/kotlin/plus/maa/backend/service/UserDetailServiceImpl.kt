package plus.maa.backend.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import plus.maa.backend.repository.UserRepository
import plus.maa.backend.repository.entity.MaaUser
import plus.maa.backend.service.model.LoginUser

/**
 * @author AnselYuki
 */
@Service
class UserDetailServiceImpl(
    private val userRepository: UserRepository,
) : UserDetailsService {
    /**
     * 查询用户信息
     *
     * @param email 用户使用邮箱登录
     * @return 用户详细信息
     * @throws UsernameNotFoundException 用户名未找到
     */
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("用户不存在")

        val permissions = collectAuthoritiesFor(user)
        // 数据封装成UserDetails返回
        return LoginUser(user, permissions)
    }

    fun collectAuthoritiesFor(user: MaaUser): Collection<GrantedAuthority> {
        val authorities = ArrayList<GrantedAuthority>()
        for (i in 0..user.status) {
            authorities.add(SimpleGrantedAuthority(i.toString()))
        }
        return authorities
    }
}
