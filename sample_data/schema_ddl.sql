DROP TABLE IF EXISTS public.opponents;
CREATE TABLE public.opponents (
    id serial NOT NULL PRIMARY KEY,
    player_id integer NOT NULL,
    start_x integer NOT NULL,
    start_y integer NOT NULL,
    map_name text NOT NULL,
    actor_name text NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL
);


DROP TABLE IF EXISTS public.items;
CREATE TABLE public.items (
    id serial NOT NULL PRIMARY KEY,
    player_id integer NOT NULL,
    map_name text NOT NULL,
    item_name text NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL
);


DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    player_name text NOT NULL,
    hp integer NOT NULL,
    armor integer NOT NULL,
    strength integer NOT NULL,
    score integer NOT NULL,
    map_name text NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    save_name text NOT NULL
);