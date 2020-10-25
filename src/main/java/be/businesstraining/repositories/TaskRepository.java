package be.businesstraining.repositories;

import org.springframework.data.repository.CrudRepository;

import be.businesstraining.models.Task;

public interface TaskRepository extends CrudRepository<Task, Integer>
{
}
