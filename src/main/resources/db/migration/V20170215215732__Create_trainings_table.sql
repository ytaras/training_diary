-- For H2 Database
create table trainings (
  id bigserial not null primary key,
  date date not null,
  comment clob not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
