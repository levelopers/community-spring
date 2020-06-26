create table Notification
(
	ID bigserial primary key,
	MESSAGE varchar(256) not null,
	GMT_CREATE bigint not null,
	IS_READ boolean default false,
	RECEIVER_ID bigint not null,
	SENDER_ID bigint not null,
	REDIRECT_URI varchar(50)
);

