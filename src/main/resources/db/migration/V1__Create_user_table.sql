create table t_user
(
	id bigserial primary key,
	USERNAME varchar(50),
	GMT_CREATE bigint,
	GMT_MODIFIED bigint,
	AVATAR_URL varchar,
	PASSWORD varchar(100)
);

