
CREATE TABLE t_user
(
 "USER_ID"      bigserial NOT NULL,
 "USERNAME"     varchar(50) NOT NULL,
 "GMT_CREATE"   bigint NOT NULL,
 "GMT_MODIFIED" bigint NOT NULL,
 "AVATAR_URL"   varchar NOT NULL,
 "PASSWORD"     varchar(100) NOT NULL,
 CONSTRAINT "PK_t_user" PRIMARY KEY ( "USER_ID" )
);
