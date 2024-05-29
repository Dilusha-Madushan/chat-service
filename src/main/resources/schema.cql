DROP TABLE IF EXISTS default_keyspace.chat;
DROP TABLE IF EXISTS default_keyspace.user_chat;

CREATE TABLE IF NOT EXISTS default_keyspace.chat (
  chatid uuid PRIMARY KEY,
  userid uuid,
  createdtime timestamp
);

CREATE TABLE IF NOT EXISTS default_keyspace.user_chat (
  id uuid PRIMARY KEY,
  userid uuid,
  chatid uuid,
  role text,
  createdtime timestamp,
  textembedding list<double>
);
