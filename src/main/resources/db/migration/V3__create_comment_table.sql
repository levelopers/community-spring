CREATE TABLE "public"."COMMENT"
(
 "COMMENT_ID"     bigserial NOT NULL GENERATED ALWAYS AS IDENTITY (
 start 1
 ),
 "PARENT_ID"      bigint NOT NULL,
 "TYPE"           integer NOT NULL,
 "GMT_CREATE"     bigint NOT NULL,
 "GMT_MODIFIED"   bigint NOT NULL,
 "LIKE_COUNT"     bigint default 0 NOT NULL,
 "CONTENT"        varchar(1024) NOT NULL,
 "COMMENT_COUNT"  integer default 0 NOT NULL,
 "COMMENTATOR_ID" bigint NOT NULL,
 CONSTRAINT "PK_COMMENT" PRIMARY KEY ( "COMMENT_ID" ),
 CONSTRAINT "FK_207" FOREIGN KEY ( "COMMENTATOR_ID" ) REFERENCES "public"."t_user" ( "USER_ID" )
);

CREATE INDEX "fkIdx_207" ON "public"."COMMENT"
(
 "COMMENTATOR_ID"
);
