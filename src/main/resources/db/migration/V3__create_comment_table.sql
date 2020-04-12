create table COMMENT
(
	ID bigserial not null
		constraint COMMENT_pk
			primary key,
	PARENT_ID bigint not null,
	TYPE integer not null,
	COMMENTATOR bigint not null,
	GMT_CREATE bigint not null,
	GMT_MODIFIED bigint not null,
	LIKE_COUNT bigint default 0,
	CONTENT varchar(1024),
	COMMENT_COUNT integer default 0
);

