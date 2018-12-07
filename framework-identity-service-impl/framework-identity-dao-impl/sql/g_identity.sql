-- Database: identity_service

-- DROP DATABASE identity_service;

CREATE DATABASE identity_service
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;


-- Sequence: public.identity_sequences

-- DROP SEQUENCE public.identity_sequences;

CREATE SEQUENCE public.identity_sequences
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
  CYCLE;
ALTER TABLE public.identity_sequences
  OWNER TO postgres;


  -- Table: public.g_identity

-- DROP TABLE public.g_identity;

CREATE TABLE public.g_identity
(
  system_name character varying(50) NOT NULL,
  sub_sys character varying(50) NOT NULL,
  module_name character varying(50) NOT NULL,
  identity_value bigint NOT NULL,
  table_full_name character varying(50) NOT NULL,
  create_time timestamp without time zone,
  update_time timestamp without time zone,
  id bigint NOT NULL DEFAULT nextval('identity_sequences'::regclass),
  increment_value integer DEFAULT 1,
  current_year bigint,
  CONSTRAINT identity_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.g_identity
  OWNER TO postgres;
