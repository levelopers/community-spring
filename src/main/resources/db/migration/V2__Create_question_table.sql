CREATE TABLE "public"."QUESTION"
(
 "QUESTION_ID"   bigserial NOT NULL GENERATED ALWAYS AS IDENTITY (
 start 1
 ),
 "TITLE"         varchar(250) NOT NULL,
 "DESCRIPTION"   varchar(1024) NOT NULL,
 "GMT_CREATE"    bigint NOT NULL,
 "GMT_MODIFIED"  bigint NOT NULL,
 "COMMENT_COUNT" integer default 0 NOT NULL,
 "VIEW_COUNT"    integer default 0 NOT NULL,
 "LIKE_COUNT"    integer default 0 NOT NULL,
 "TAG"           varchar(256),
 "CREATOR_ID"    bigint NOT NULL,
 CONSTRAINT "PK_QUESTION" PRIMARY KEY ( "QUESTION_ID" ),
 CONSTRAINT "FK_198" FOREIGN KEY ( "CREATOR_ID" ) REFERENCES "public"."t_user" ( "USER_ID" )
);

CREATE INDEX "fkIdx_198" ON "public"."QUESTION"
(
 "CREATOR_ID"
);
