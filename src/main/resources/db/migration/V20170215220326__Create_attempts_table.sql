-- For H2 Database
create table attempts (
  id bigserial not null primary key,
  training_id bigint not null,
  exercise_id bigint not null,
  shooter_id bigint not null,
  result Decimal(5,2),
  created_at timestamp not null,
  updated_at timestamp not null,
  constraint attempts_training_id foreign key (training_id) references trainings (id),
  constraint attempts_shooter_id foreign key (shooter_id) references shooters (id),
  constraint attempts_exercise_id foreign key (exercise_id) references exercises (id)
)
