DELETE FROM tweet_link;
DELETE FROM tweet;

INSERT INTO tweet(id, publisher, tweet, discarded, pre2015migrationstatus)
VALUES (1, 'Author1', 'This is a short tweet', false, 0);

INSERT INTO tweet(id, publisher, tweet, discarded, pre2015migrationstatus)
VALUES (2, 'Author2', 'This is a short tweet', false, 0);

INSERT INTO tweet(id, publisher, tweet, discarded, pre2015migrationstatus)
VALUES (3, 'Author1', 'Another tweet from author 1', false, 99);

INSERT INTO tweet(id, publisher, tweet, discarded, pre2015migrationstatus)
VALUES (4, 'Author1', 'A discarded tweet', true, 0);