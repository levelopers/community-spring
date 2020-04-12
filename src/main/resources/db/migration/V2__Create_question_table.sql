create table QUESTION
(
	ID bigserial not null
		constraint QUESTION_pk
			primary key,
	TITLE varchar(50),
	DESCRIPTION text,
	GMT_CREATE bigint,
	GMT_MODIFIED bigint,
	CREATOR bigint not null,
	COMMENT_COUNT integer default 0,
	VIEW_COUNT integer default 0,
	LIKE_COUNT integer default 0,
	TAG varchar(256)
);

