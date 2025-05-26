--
-- PostgreSQL database dump
--

-- Dumped from database version 14.3
-- Dumped by pg_dump version 14.3

-- Started on 2025-05-26 18:22:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3332 (class 1262 OID 17987)
-- Name: event_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE event_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';


ALTER DATABASE event_db OWNER TO postgres;

\connect event_db

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3333 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 211 (class 1259 OID 18086)
-- Name: ems_attendance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ems_attendance (
    event_id character varying(100) NOT NULL,
    user_id character varying(100) NOT NULL,
    status character varying(50) NOT NULL,
    attended_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.ems_attendance OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 18043)
-- Name: ems_event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ems_event (
    id character varying(100) NOT NULL,
    title character varying(100) NOT NULL,
    description text NOT NULL,
    host_id character varying(100) NOT NULL,
    start_time timestamp without time zone NOT NULL,
    end_time timestamp without time zone NOT NULL,
    location character varying(255) NOT NULL,
    visibility character varying(20) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone
);


ALTER TABLE public.ems_event OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 18032)
-- Name: ems_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ems_user (
    id character varying(100) NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    email character varying(255) NOT NULL,
    role character varying(50) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone
);


ALTER TABLE public.ems_user OWNER TO postgres;

--
-- TOC entry 3326 (class 0 OID 18086)
-- Dependencies: 211
-- Data for Name: ems_attendance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ems_attendance (event_id, user_id, status, attended_at) FROM stdin;
ad9e1564-feb7-46c7-9cf7-30c5b8fadb63	0fffb2b2-1638-4fa0-9f90-0b1f3e75945e	GOING	2025-05-26 18:12:17.499906
ad9e1564-feb7-46c7-9cf7-30c5b8fadb63	9c795a5c-d969-46f5-a209-c00559af736e	GOING	2025-05-26 18:13:12.647056
232dd4fd-899f-46ff-b37f-ed0523f9c49b	9c795a5c-d969-46f5-a209-c00559af736e	DECLINED	2025-05-26 18:13:09.744823
\.


--
-- TOC entry 3325 (class 0 OID 18043)
-- Dependencies: 210
-- Data for Name: ems_event; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ems_event (id, title, description, host_id, start_time, end_time, location, visibility, created_at, updated_at) FROM stdin;
ad9e1564-feb7-46c7-9cf7-30c5b8fadb63	Summer Coding Bootcamp	Learn web development from industry professionals in this 5-day hands-on bootcamp. No prior experience required! Lunch and course materials will be provided.	0fffb2b2-1638-4fa0-9f90-0b1f3e75945e	2025-06-01 06:30:00	2025-06-10 07:30:00	Colombo 03, Sri Lanka	PUBLIC	2025-05-26 18:09:09.455224	2025-05-26 18:09:09.455224
232dd4fd-899f-46ff-b37f-ed0523f9c49b	Outdoor Movie Night	Bring a blanket, grab some popcorn, and enjoy a movie under the stars at Riverside Park. This week's feature: Back to the Future.	0fffb2b2-1638-4fa0-9f90-0b1f3e75945e	2025-06-19 07:30:00	2025-07-03 08:30:00	Negombo, Sri Lanka	PRIVATE	2025-05-26 18:12:09.898429	2025-05-26 18:12:09.898429
\.


--
-- TOC entry 3324 (class 0 OID 18032)
-- Dependencies: 209
-- Data for Name: ems_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ems_user (id, username, password, name, email, role, created_at, updated_at) FROM stdin;
0fffb2b2-1638-4fa0-9f90-0b1f3e75945e	admin	$2a$12$fzXXFpahubT8ppn1aEKtF.fJQ39mzEX02.JqlhtNQudcJ6yK0QIrq	Gayathra Madubashana	ranawakamadubashana@gmail.com	ADMIN	2025-05-26 12:35:45.492	\N
9c795a5c-d969-46f5-a209-c00559af736e	user	$2a$12$Gc7EmQAxrPFiqmT0I.ofTuBFVgysEKkOGm.Tjzcd.gik0sMdH7HIG	Nuwan Lakshan	nuwan@gmail.com	USER	2025-05-26 12:42:23.877	\N
\.


--
-- TOC entry 3181 (class 2606 OID 18091)
-- Name: ems_attendance ems_attendance_pky; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ems_attendance
    ADD CONSTRAINT ems_attendance_pky PRIMARY KEY (event_id, user_id);


--
-- TOC entry 3179 (class 2606 OID 18049)
-- Name: ems_event ems_event_pky; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ems_event
    ADD CONSTRAINT ems_event_pky PRIMARY KEY (id);


--
-- TOC entry 3173 (class 2606 OID 18042)
-- Name: ems_user ems_user_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ems_user
    ADD CONSTRAINT ems_user_email_key UNIQUE (email);


--
-- TOC entry 3175 (class 2606 OID 18038)
-- Name: ems_user ems_user_pky; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ems_user
    ADD CONSTRAINT ems_user_pky PRIMARY KEY (id);


--
-- TOC entry 3177 (class 2606 OID 18040)
-- Name: ems_user ems_user_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ems_user
    ADD CONSTRAINT ems_user_username_key UNIQUE (username);


--
-- TOC entry 3183 (class 2606 OID 18092)
-- Name: ems_attendance ems_attendance_fky1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ems_attendance
    ADD CONSTRAINT ems_attendance_fky1 FOREIGN KEY (event_id) REFERENCES public.ems_event(id) ON DELETE CASCADE;


--
-- TOC entry 3184 (class 2606 OID 18097)
-- Name: ems_attendance ems_attendance_fky2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ems_attendance
    ADD CONSTRAINT ems_attendance_fky2 FOREIGN KEY (user_id) REFERENCES public.ems_user(id) ON DELETE CASCADE;


--
-- TOC entry 3182 (class 2606 OID 18050)
-- Name: ems_event ems_event_fky1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ems_event
    ADD CONSTRAINT ems_event_fky1 FOREIGN KEY (host_id) REFERENCES public.ems_user(id);


-- Completed on 2025-05-26 18:22:49

--
-- PostgreSQL database dump complete
--

