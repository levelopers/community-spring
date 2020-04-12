create table t_user
(
	id bigserial not null
		constraint user_pk
			primary key,
	ACCOUNT_ID varchar(100),
	USERNAME varchar(50),
	TOKEN char(36),
	GMT_CREATE bigint,
	GMT_MODIFIED bigint,
	AVATAR_URL varchar(100),
	PASSWORD varchar(100)
);

