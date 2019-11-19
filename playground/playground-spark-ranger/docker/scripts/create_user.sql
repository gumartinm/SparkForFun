CREATE USER rangerroot WITH PASSWORD 'rangerroot';
CREATE USER rangeradmin WITH PASSWORD 'rangeradmin';
CREATE DATABASE ranger;
GRANT ALL PRIVILEGES ON DATABASE ranger TO rangerroot;
GRANT ALL PRIVILEGES ON DATABASE ranger TO rangeradmin;
