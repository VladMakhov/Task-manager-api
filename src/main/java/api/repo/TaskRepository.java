package api.repo;

import api.model.Task;
import api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByAuthor(User author);
    @Query(nativeQuery = true, value = """
                    select task_id from tasks_list_of_executors
                    where list_of_executors_id = :executorId
            """)
    List<Integer> findAllByExecutorId(@Param("executorId") int id);
}
