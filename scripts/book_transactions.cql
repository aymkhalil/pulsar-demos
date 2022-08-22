CREATE TABLE marketplace.book_transactions (id UUID, created date,  publisher TEXT , type TEXT, royalty DOUBLE, PRIMARY KEY (created, publisher, id));

SELECT publisher, SUM(royalty) FROM marketplace.book_transactions WHERE created = 'YYYY-MM-DD' GROUP BY publisher;
