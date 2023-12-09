package api.repo;

import api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String email);

    @Query(nativeQuery = true, value = """
            select * from users
            where id in (:ids);
            """)
    List<User> findAllByIds(List<Integer> ids);
}
