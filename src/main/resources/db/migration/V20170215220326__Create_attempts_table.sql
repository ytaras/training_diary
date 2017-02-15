-- For H2 Database
create table attempts (
  id bigserial not null primary key,
  training_id bigint not null references trainings (id),
  exercise_id bigint not null references trainings (id),
  shooter_id bigint not null references trainings (id),
  result Decimal(5,2),
  created_at timestamp not null,
  updated_at timestamp not null
)
