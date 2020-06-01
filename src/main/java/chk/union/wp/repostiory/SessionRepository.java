package chk.union.wp.repostiory;

import chk.union.wp.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findFirstBySecurityToken(String securityToken);
    void deleteBySecurityToken(String securityToken);
}
