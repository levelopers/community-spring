
INSERT INTO t_user (username,password,AVATAR_URL) VALUES ('zack',
'$2a$10$VpsnPMGEEi4LmqBSKeqVXep4VHwkG1luPtrEewN1d6mkAjCUK1Fs6',
'https://storage.googleapis.com/spring-forum-1cec4.appspot.com/images/profile.jfif?GoogleAccessId=firebase-adminsdk-t4is3@spring-forum-1cec4.iam.gserviceaccount.com&Expires=1623528086&Signature=PrXxWQHWKDTKzvHNRehKJo9k3HLXm%2FnCAZYDmBMNCOXmv86yvSZg8z%2FL7Ul%2F7PAbsUjub%2BoOKLY8hY4V9tyRkct%2F7WbAPMJhsSzO7qXleHZg6QeExnHqB9PwFUENuAD2ctfXDqmpbvdiRbj5YRHFcb9lDzYHiLhovGhxBcj7E3B2IzyC%2FL%2FrMlYyYuhxi0s%2BBHZake4GYek780kmiSufCnFvhWiqQaSY3MZ1zrp0%2BsZ1LW%2BGs4TPWPjmcVK0S5rK7iZ2gh4sptgzrtitGI2UwyzoW0yNplvU0W6QWFAYQHz6YGeWquluh4stV41v6jEbziU9hKYdlNVV92zms8fngA%3D%3D');
INSERT INTO t_user (username,password,AVATAR_URL) VALUES ('allen',
'$2a$10$VpsnPMGEEi4LmqBSKeqVXep4VHwkG1luPtrEewN1d6mkAjCUK1Fs6',
'https://storage.googleapis.com/spring-forum-1cec4.appspot.com/images/profile1.jfif?GoogleAccessId=firebase-adminsdk-t4is3@spring-forum-1cec4.iam.gserviceaccount.com&Expires=1623528500&Signature=aAsgX9iJ0%2FQvbDSfQRZxZgN1Gbc8uU%2FUZANNbFVyeO8YwUMjDOZxz29IEZeLIUKQW9p8L71qDqvvPzxHnBfVW9pNeGVdsf2SZKi1nMaicPcBlfP4Ymivl07O17eaEOKBBQB4d74ksBOCvHPwf%2BlOMLtmfJUQ0SIomOP8awXRhlZPiH7eHa9jcRuHxHkafSXAFBMsKQjT4U0SKUFU9kANjzY2XRxVb5Bd5x8IESaRGUHShbgsuFq06dRDvZgzdTpTpSb0r7OA62IvEeTBJT5sa0yxJlgbZKsuQG1ynFtdqo5ll%2Bq8tfbWy3Br55hVGwHD9dIzstSEyr7WvLt8qmfNYw%3D%3D');

INSERT INTO QUESTION (
TITLE,
DESCRIPTION,
GMT_CREATE,
GMT_MODIFIED,
CREATOR,
COMMENT_COUNT,
VIEW_COUNT,
LIKE_COUNT,
TAG) VALUES (
'What is the difference between Promises and Observables?',
'What is the difference between Promise and Observable in Angular?

An example on each would be helpful in understanding both the cases. In what scenario can we use each case?',
1585385895854,
1585385895854,
1,
2,
1,
1,
'tag1'
);


INSERT INTO QUESTION (
TITLE,
DESCRIPTION,
GMT_CREATE,
GMT_MODIFIED,
CREATOR,
COMMENT_COUNT,
VIEW_COUNT,
LIKE_COUNT,
TAG) VALUES (
'What is the difference between angular-route and angular-ui-router?',
'I''m planning to use AngularJS in my big applications. So I''m in the process to find out the right modules to use.

What is the difference between ngRoute (angular-route.js) and ui-router (angular-ui-router.js) modules?

In many articles when ngRoute is used, route is configured with $routeProvider. However, when used with ui-router, route is configured with $stateProvider and $urlRouterProvider.',
1585385895854,
1585385895854,
2,
0,
2,
2,
'tag1,tag2'
);

INSERT INTO QUESTION (
TITLE,
DESCRIPTION,
GMT_CREATE,
GMT_MODIFIED,
CREATOR,
COMMENT_COUNT,
VIEW_COUNT,
LIKE_COUNT,
TAG) VALUES (
'SORRY HORDE',
'You’re gonna have to deal with longer AV queues now because of the AV changes. Instead of actually fixing the BG they made things worse for everyone again. Horde had to wait in a long Q but they got the win every time. Ally has 5 minute Qs but they get good rep from druids in trade of a win.',
1585385895854,
1585385895854,
1,
0,
1,
13,
'For the Horde'
);

INSERT INTO COMMENT (
PARENT_ID,
TYPE,
COMMENTATOR,
GMT_CREATE,
GMT_MODIFIED,
LIKE_COUNT,
CONTENT,
COMMENT_COUNT) VALUES (
1,
1,
1,
1585385895854,
1585385895854,
12,
'Promise

A Promise handles a single event when an async operation completes or fails.
Note: There are Promise libraries out there that support cancellation, but ES6 Promise doesn''t so far.
Observable

An Observable is like a Stream (in many languages) and allows to pass zero or more events where the callback is called for each event.

Often Observable is preferred over Promise because it provides the features of Promise and more. With Observable it doesn''t matter if you want to handle 0, 1, or multiple events. You can utilize the same API in each case.

Observable also has the advantage over Promise to be cancelable. If the result of an HTTP request to a server or some other expensive async operation isn''t needed anymore, the Subscription of an Observable allows to cancel the subscription, while a Promise will eventually call the success or failed callback even when you don''t need the notification or the result it provides anymore.
',
0
);


INSERT INTO COMMENT (
PARENT_ID,
TYPE,
COMMENTATOR,
GMT_CREATE,
GMT_MODIFIED,
LIKE_COUNT,
CONTENT,
COMMENT_COUNT) VALUES (
1,
1,
2,
1585385895854,
1585385895854,
13,
'As far as I am using Http in Angular, I agree that in the normal use cases there is not much difference when using Observable over Promise. None of the advantages are really relevant here in practice. Hope I can see some advanced use case in the future :)',
1
);

INSERT INTO COMMENT (
PARENT_ID,
TYPE,
COMMENTATOR,
GMT_CREATE,
GMT_MODIFIED,
LIKE_COUNT,
CONTENT,
COMMENT_COUNT) VALUES (
2,
2,
1,
1585385895854,
1585385895854,
13,
'If you want to use the reactive style, just use observables everywhere. If you have observables only you can easy compose. If you mix them it''s not so clean anymore. If you don''t care about reactive style, you can use promise for single events where you don''t care about cancelable and observable for streams of events.',
0
);

INSERT INTO NOTIFICATION (
MESSAGE,
GMT_CREATE,
IS_READ,
RECEIVER_ID,
SENDER_ID,
REDIRECT_URI) VALUES (
'allen just make a comment to your question SORRY HORDE',
1585385895854,
false,
1,
2,
'/questions/3'
);

INSERT INTO NOTIFICATION (
MESSAGE,
GMT_CREATE,
IS_READ,
RECEIVER_ID,
SENDER_ID,
REDIRECT_URI) VALUES (
'allen just make a comment to your question What is the difference between Promises and Observables?',
1585385895854,
true,
1,
2,
'/questions/1'
);
