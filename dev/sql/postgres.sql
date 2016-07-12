--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: btree_gin; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS btree_gin WITH SCHEMA public;


--
-- Name: EXTENSION btree_gin; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION btree_gin IS 'support for indexing common datatypes in GIN';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: browser_link; Type: TABLE; Schema: public; Owner: historify; Tablespace: 
--

CREATE TABLE browser_link (
    id bigserial NOT NULL PRIMARY KEY,
    client character varying(255) NOT NULL,
    description text,
    meta_data character varying(255),
    title character varying(255) NOT NULL,
    uri character varying(255) NOT NULL,
    visited_at timestamp without time zone NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.browser_link OWNER TO historify;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: historify
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO historify;

--
-- Name: users; Type: TABLE; Schema: public; Owner: historify; Tablespace: 
--

CREATE TABLE users (
    id bigserial NOT NULL PRIMARY KEY,
    email character varying(255) NOT NULL,
    last_login character varying(255),
    password character varying(100) NOT NULL,
    role character varying(255),
    token character varying(255),
    user_name character varying(100) NOT NULL
);


ALTER TABLE public.users OWNER TO historify;

--
-- Name: uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: historify; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: uk_k8d0f2n7n88w1a16yhua64onx; Type: CONSTRAINT; Schema: public; Owner: historify; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_k8d0f2n7n88w1a16yhua64onx UNIQUE (user_name);


--
-- Name: user_index; Type: INDEX; Schema: public; Owner: historify; Tablespace: 
--

CREATE INDEX user_index ON browser_link USING btree (user_id);


--
-- Name: fk_90wtphkdtfleo9ddap43c6ukx; Type: FK CONSTRAINT; Schema: public; Owner: historify
--

ALTER TABLE ONLY browser_link
    ADD CONSTRAINT fk_90wtphkdtfleo9ddap43c6ukx FOREIGN KEY (user_id) REFERENCES users(id);
    
ALTER TABLE browser_link ADD COLUMN uri_keywords text NOT NULL DEFAULT '';


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

-- drop MATERIALIZED VIEW browser_link_search;

CREATE MATERIALIZED VIEW browser_link_search AS 
select *,
  to_tsvector(title) || 
  to_tsvector(coalesce((string_agg(description, ' ')), '')) ||
  to_tsvector(coalesce((string_agg(uri_keywords, ' ')), ''))
 as document from browser_link
group by id;

CREATE INDEX idx_browser_link_search ON browser_link_search USING gin(document);

create or replace function refresh_browser_link_search()
returns trigger language plpgsql
security definer
as $$
begin
    refresh materialized view browser_link_search;
    return null;
end $$;

create trigger refresh_browser_link_search
after insert or update or delete or truncate
on browser_link for each statement 
execute procedure refresh_browser_link_search();

GRANT ALL ON TABLE public.browser_link_search TO historify;


-- select * from browser_link_search where user_id = 10 and document @@ to_tsquery('rerik')



ALTER TABLE browser_link ADD COLUMN read_later boolean DEFAULT false;

ALTER TABLE browser_link ADD COLUMN tags text;
