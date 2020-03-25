--
-- PostgreSQL database dump
--

-- Dumped from database version 11.7 (Ubuntu 11.7-2.pgdg18.04+1)
-- Dumped by pg_dump version 12.2 (Ubuntu 12.2-2.pgdg18.04+1)

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

SET default_tablespace = '';

--
-- Name: asset; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.asset (
    asset_id integer NOT NULL,
    name character varying(50) NOT NULL,
    type character varying(50) NOT NULL,
    qr_code character varying(50),
    asset_category character varying(50) NOT NULL,
    asset_department integer NOT NULL,
    status character varying(15) DEFAULT 'FREE'::character varying NOT NULL
);


ALTER TABLE public.asset OWNER TO postgres;

--
-- Name: asset_asset_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.asset_asset_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.asset_asset_id_seq OWNER TO postgres;

--
-- Name: asset_asset_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asset_asset_id_seq OWNED BY public.asset.asset_id;


--
-- Name: asset_fault; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.asset_fault (
    asset_failt_id integer NOT NULL,
    fault_id integer,
    asset_id integer NOT NULL,
    time_of_failure date NOT NULL,
    fix_time date,
    fixable boolean
);


ALTER TABLE public.asset_fault OWNER TO postgres;

--
-- Name: asset_fault_asset_failt_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.asset_fault_asset_failt_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.asset_fault_asset_failt_id_seq OWNER TO postgres;

--
-- Name: asset_fault_asset_failt_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asset_fault_asset_failt_id_seq OWNED BY public.asset_fault.asset_failt_id;


--
-- Name: asset_tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.asset_tag (
    asset_tag_id integer NOT NULL,
    used_tag character varying(50) NOT NULL,
    used_asset integer NOT NULL
);


ALTER TABLE public.asset_tag OWNER TO postgres;

--
-- Name: asset_tag_asset_tag_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.asset_tag_asset_tag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.asset_tag_asset_tag_id_seq OWNER TO postgres;

--
-- Name: asset_tag_asset_tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.asset_tag_asset_tag_id_seq OWNED BY public.asset_tag.asset_tag_id;


--
-- Name: department; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.department (
    department_id integer NOT NULL,
    department_name character varying(50) NOT NULL,
    department_location integer NOT NULL
);


ALTER TABLE public.department OWNER TO postgres;

--
-- Name: department_department_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.department_department_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.department_department_id_seq OWNER TO postgres;

--
-- Name: department_department_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.department_department_id_seq OWNED BY public.department.department_id;


--
-- Name: fault; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fault (
    fault_id integer NOT NULL,
    type_of_fault character varying(50) NOT NULL
);


ALTER TABLE public.fault OWNER TO postgres;

--
-- Name: fault_fault_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fault_fault_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.fault_fault_id_seq OWNER TO postgres;

--
-- Name: fault_fault_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fault_fault_id_seq OWNED BY public.fault.fault_id;


--
-- Name: location; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.location (
    location_id integer NOT NULL,
    state character varying(50) NOT NULL,
    address character varying(50) NOT NULL,
    postcode integer NOT NULL
);


ALTER TABLE public.location OWNER TO postgres;

--
-- Name: location_location_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.location_location_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.location_location_id_seq OWNER TO postgres;

--
-- Name: location_location_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.location_location_id_seq OWNED BY public.location.location_id;


--
-- Name: tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tag (
    tag_name character varying(50) NOT NULL
);


ALTER TABLE public.tag OWNER TO postgres;

--
-- Name: ticket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ticket (
    invoice_id integer NOT NULL,
    time_created date NOT NULL,
    time_accepted date,
    time_assigned date,
    user_info integer,
    asset_info integer NOT NULL,
    comment character varying(200)
);


ALTER TABLE public.ticket OWNER TO postgres;

--
-- Name: ticket_invoice_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ticket_invoice_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ticket_invoice_id_seq OWNER TO postgres;

--
-- Name: ticket_invoice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ticket_invoice_id_seq OWNED BY public.ticket.invoice_id;


--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    user_id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    surname character varying(50) NOT NULL,
    city character varying(50) NOT NULL,
    address character varying(50) NOT NULL,
    postcode integer NOT NULL,
    user_department integer,
    login character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    is_admin boolean NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- Name: user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_user_id_seq OWNER TO postgres;

--
-- Name: user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_user_id_seq OWNED BY public."user".user_id;


--
-- Name: asset asset_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset ALTER COLUMN asset_id SET DEFAULT nextval('public.asset_asset_id_seq'::regclass);


--
-- Name: asset_fault asset_failt_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset_fault ALTER COLUMN asset_failt_id SET DEFAULT nextval('public.asset_fault_asset_failt_id_seq'::regclass);


--
-- Name: asset_tag asset_tag_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset_tag ALTER COLUMN asset_tag_id SET DEFAULT nextval('public.asset_tag_asset_tag_id_seq'::regclass);


--
-- Name: department department_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department ALTER COLUMN department_id SET DEFAULT nextval('public.department_department_id_seq'::regclass);


--
-- Name: fault fault_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fault ALTER COLUMN fault_id SET DEFAULT nextval('public.fault_fault_id_seq'::regclass);


--
-- Name: location location_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.location ALTER COLUMN location_id SET DEFAULT nextval('public.location_location_id_seq'::regclass);


--
-- Name: ticket invoice_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket ALTER COLUMN invoice_id SET DEFAULT nextval('public.ticket_invoice_id_seq'::regclass);


--
-- Name: user user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user" ALTER COLUMN user_id SET DEFAULT nextval('public.user_user_id_seq'::regclass);


--
-- Data for Name: asset; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.asset (asset_id, name, type, qr_code, asset_category, asset_department, status) FROM stdin;
3	sdfasdgas	asdhadfha	\N	askdhkaks	1	FREE
1506	Youtube Premium	Single-use	4136967916545	Consumable	455	FREE
1507	Dell	Switch	1612202817073	Asset	456	FREE
1508	Microsoft	Phone	7438502390665	Asset	457	FREE
1509	A4tech	Phone	9964770434629	Asset	458	FREE
1510	Youtube Premium	Single-use	7454374827603	Consumable	459	FREE
1511	Youtube Premium	Reusable	0911054364355	Consumable	460	FREE
1512	Adobe Ilustrator	Single-use	1103909581058	Consumable	461	FREE
1513	Youtube Premium	Reusable	2261910650817	Consumable	462	FREE
1514	Adobe Photoshop	Single-use	4202979603367	Consumable	463	FREE
1515	A4tech	PC	6968355231712	Asset	464	FREE
1516	Corsair	Phone	8748949391620	Asset	465	FREE
1517	Logitech	Phone	9729243461232	Asset	466	FREE
1518	Windows 10	Reusable	5852428220899	Consumable	467	FREE
1519	Sony	Mouse	0340384557920	Asset	468	FREE
1520	A4tech	Monitor	9913651587077	Asset	469	FREE
1521	Adobe Ilustrator	Reusable	4277018050563	Consumable	470	FREE
1522	Corsair	Router	7243000296896	Asset	471	FREE
1523	Adobe Photoshop	Single-use	2619669430578	Consumable	472	FREE
1524	Microsoft	Router	6494676834580	Asset	473	FREE
1525	Apple	Keyboard	1742912562618	Asset	474	FREE
1526	MSSQL	Reusable	2637173094673	Consumable	475	FREE
1527	Youtube Premium	Single-use	4053442352073	Consumable	476	FREE
1528	Adobe Photoshop	Reusable	5458843210035	Consumable	477	FREE
1529	Adobe Ilustrator	Reusable	2337805585004	Consumable	478	FREE
1530	Youtube Premium	Single-use	2610010352105	Consumable	479	FREE
1531	A4tech	Switch	5758547161900	Asset	480	FREE
1532	Youtube Premium	Single-use	6084689334643	Consumable	481	FREE
1533	Adobe Photoshop	Single-use	6598719329863	Consumable	482	FREE
1534	Apple	PC	3551584630185	Asset	483	FREE
1535	Toshiba	PC	7794856752000	Asset	484	FREE
1536	Windows 10	Reusable	5836359250729	Consumable	485	FREE
1537	Toshiba	Keyboard	3229511354953	Asset	486	FREE
1538	Microsoft Office	Single-use	8385719907577	Consumable	487	FREE
1539	Toshiba	Keyboard	8216593357366	Asset	488	FREE
1540	A4tech	Router	0588326478784	Asset	489	FREE
1541	Sony	PC	5210416035120	Asset	490	FREE
1542	Microsoft	Keyboard	8479798256616	Asset	491	FREE
1543	Lenovo	PC	4915807211831	Asset	492	FREE
1544	Jetbrains Account	Single-use	8451879419394	Consumable	493	FREE
1545	Apple	Monitor	8111973322685	Asset	494	FREE
1546	Youtube Premium	Reusable	4057232508732	Consumable	495	FREE
1547	Corsair	PC	4427646432426	Asset	496	FREE
1548	Toshiba	Router	2771721264887	Asset	497	FREE
1549	Toshiba	Switch	6335937536464	Asset	498	FREE
1550	Windows 7	Reusable	0637641589931	Consumable	499	FREE
1551	Windows 10	Single-use	9447756967545	Consumable	500	FREE
1552	Dell	Monitor	2675816387737	Asset	501	FREE
1553	Windows 10	Single-use	9036465581779	Consumable	502	FREE
1554	Windows 7	Single-use	2116088203528	Consumable	503	FREE
1555	Lenovo	PC	1385122425979	Asset	504	FREE
1556	Microsoft	Switch	3543696829960	Asset	505	FREE
1557	Sony	Mouse	5574057080568	Asset	506	FREE
1558	Windows 10	Reusable	4013749598710	Consumable	507	FREE
1559	Toshiba	Mouse	0186336911361	Asset	508	FREE
1560	Microsoft Office	Single-use	5583572855223	Consumable	509	FREE
1561	Youtube Premium	Reusable	2038757810236	Consumable	510	FREE
1562	Jetbrains Account	Single-use	7770812395405	Consumable	511	FREE
1563	Adobe Ilustrator	Reusable	8338740644898	Consumable	512	FREE
1564	Jetbrains Account	Reusable	4436463540634	Consumable	513	FREE
1565	Adobe Ilustrator	Single-use	7831747114698	Consumable	514	FREE
1566	Adobe Photoshop	Reusable	7317749855918	Consumable	515	FREE
1567	Microsoft Office	Reusable	4960390186127	Consumable	516	FREE
1568	Adobe Ilustrator	Reusable	8103684959151	Consumable	517	FREE
1569	Logitech	Mouse	4188781720907	Asset	518	FREE
1570	Apple	Switch	7928606127224	Asset	519	FREE
1571	Windows 10	Single-use	7395151181139	Consumable	520	FREE
1572	Windows 7	Reusable	5825888649357	Consumable	521	FREE
1573	Lenovo	Phone	4685096857569	Asset	522	FREE
1574	Adobe Ilustrator	Reusable	8035461057371	Consumable	523	FREE
1575	Corsair	Mouse	7473443245575	Asset	524	FREE
1576	Dell	PC	7661066351043	Asset	525	FREE
1577	Microsoft	Notebook	3968692258314	Asset	526	FREE
1578	A4tech	Phone	0120168285719	Asset	527	FREE
1579	Microsoft Office	Reusable	0183343610157	Consumable	528	FREE
1580	Lenovo	Router	4429594474558	Asset	529	FREE
1581	Microsoft Office	Reusable	7236855824192	Consumable	530	FREE
1582	Windows 10	Reusable	9606820845985	Consumable	531	FREE
1583	Adobe Photoshop	Reusable	2114748686704	Consumable	532	FREE
1584	Apple	Phone	8340381643246	Asset	533	FREE
1585	Dell	Keyboard	3120654164006	Asset	534	FREE
1586	Windows 10	Reusable	6500588346636	Consumable	535	FREE
1587	Apple	Phone	4310562009820	Asset	536	FREE
1588	Youtube Premium	Single-use	7122523410302	Consumable	537	FREE
1589	Jetbrains Account	Single-use	3152087050991	Consumable	538	FREE
1590	Apple	Phone	0530498180354	Asset	539	FREE
1591	Youtube Premium	Single-use	3935414323339	Consumable	540	FREE
1592	Microsoft Office	Reusable	4635252622944	Consumable	541	FREE
1593	Jetbrains Account	Reusable	4103964917523	Consumable	542	FREE
1594	Adobe Photoshop	Reusable	6695344176616	Consumable	543	FREE
1595	Windows 7	Single-use	3231568088743	Consumable	544	FREE
1596	Windows 10	Reusable	2175885975045	Consumable	545	FREE
1597	Toshiba	Switch	8949311075432	Asset	546	FREE
1598	Logitech	Monitor	7934257923863	Asset	547	FREE
1599	Apple	Mouse	9302238807019	Asset	548	FREE
1600	Toshiba	Router	2517949854017	Asset	549	FREE
1601	Windows 7	Reusable	8860013589518	Consumable	550	FREE
1602	Logitech	Mouse	8016995948885	Asset	551	FREE
1603	Adobe Photoshop	Single-use	2797672219707	Consumable	552	FREE
1604	MSSQL	Single-use	4839891554622	Consumable	553	FREE
1605	Microsoft	Monitor	7593210459070	Asset	554	FREE
1606	Logitech	Notebook	5077928514723	Asset	555	FREE
1607	MSSQL	Single-use	9692309127178	Consumable	556	FREE
1608	Youtube Premium	Reusable	3144362612833	Consumable	557	FREE
1609	Microsoft Office	Single-use	4350481782237	Consumable	558	FREE
1610	Apple	Keyboard	1314282378801	Asset	559	FREE
1611	Jetbrains Account	Reusable	2755875888496	Consumable	560	FREE
1612	Sony	Router	5657495988594	Asset	561	FREE
1613	Windows 7	Single-use	0233048738868	Consumable	562	FREE
1614	Windows 10	Reusable	0271257403221	Consumable	563	FREE
1615	Windows 10	Reusable	1848327433328	Consumable	564	FREE
1616	MSSQL	Reusable	0688006900977	Consumable	565	FREE
1617	Toshiba	Router	8776995049707	Asset	566	FREE
1618	Microsoft Office	Reusable	2058908164438	Consumable	567	FREE
1619	Youtube Premium	Single-use	8811846214740	Consumable	568	FREE
1620	Apple	Router	5016248085377	Asset	569	FREE
1621	Jetbrains Account	Single-use	9841324502570	Consumable	570	FREE
1622	Jetbrains Account	Single-use	5905201869386	Consumable	571	FREE
1623	Sony	Notebook	7080459560525	Asset	572	FREE
1624	Windows 7	Reusable	3534305929249	Consumable	573	FREE
1625	Logitech	Router	1176481077455	Asset	574	FREE
1626	Dell	Phone	2608008266329	Asset	575	FREE
1627	Adobe Ilustrator	Reusable	4501521828457	Consumable	576	FREE
1628	Windows 10	Single-use	9530935988124	Consumable	577	FREE
1629	Adobe Ilustrator	Reusable	2497840827771	Consumable	578	FREE
1630	Corsair	Keyboard	4017849176564	Asset	579	FREE
1631	Adobe Ilustrator	Single-use	9222659065587	Consumable	580	FREE
1632	Corsair	Mouse	7952790603097	Asset	581	FREE
1633	Toshiba	Notebook	7103688670046	Asset	582	FREE
1634	Adobe Photoshop	Reusable	5193255803665	Consumable	583	FREE
1635	Youtube Premium	Single-use	3883738828378	Consumable	584	FREE
1636	Adobe Photoshop	Single-use	3975056397657	Consumable	585	FREE
1637	Adobe Photoshop	Reusable	1788537076826	Consumable	586	FREE
1638	Logitech	Monitor	5601343933984	Asset	587	FREE
1639	Adobe Ilustrator	Single-use	3538219452886	Consumable	588	FREE
1640	Dell	Monitor	4635461390993	Asset	589	FREE
1641	Windows 10	Single-use	0939770129867	Consumable	590	FREE
1642	MSSQL	Reusable	3444050526443	Consumable	591	FREE
1643	Microsoft Office	Reusable	5796796982020	Consumable	592	FREE
1644	Sony	Mouse	4495741317436	Asset	593	FREE
1645	MSSQL	Reusable	3926392676098	Consumable	594	FREE
1646	Sony	Phone	0105273252375	Asset	595	FREE
1647	Dell	Notebook	3730753126258	Asset	596	FREE
1648	A4tech	Router	4483521993864	Asset	597	FREE
1649	Dell	Monitor	2170374648332	Asset	598	FREE
1650	Apple	Notebook	6640571527533	Asset	599	FREE
1651	Dell	Router	6955092175331	Asset	600	FREE
1652	Apple	Phone	2812942102470	Asset	601	FREE
1653	A4tech	Router	9610522888232	Asset	602	FREE
1654	Adobe Photoshop	Reusable	3978090416872	Consumable	603	FREE
1655	Toshiba	Phone	9527189526660	Asset	603	FREE
1656	Dell	Keyboard	9385842626351	Asset	455	FREE
1657	Dell	PC	8258470209627	Asset	456	FREE
1658	Jetbrains Account	Single-use	4420259076742	Consumable	457	FREE
1659	Adobe Photoshop	Reusable	1038128073100	Consumable	458	FREE
1660	Windows 7	Reusable	0376193750245	Consumable	459	FREE
1661	Sony	Notebook	5191202130673	Asset	460	FREE
1662	Adobe Photoshop	Reusable	3305403862307	Consumable	461	FREE
1663	Logitech	Phone	3587897990715	Asset	462	FREE
1664	Microsoft	PC	4816955164249	Asset	463	FREE
1665	Microsoft	Switch	2606482911537	Asset	464	FREE
1666	Dell	Phone	1853597449177	Asset	465	FREE
1667	Corsair	Monitor	0333936365418	Asset	466	FREE
1668	Sony	Notebook	8360214684093	Asset	467	FREE
1669	Corsair	Phone	6977639019861	Asset	468	FREE
1670	Apple	Monitor	8897666007038	Asset	469	FREE
1671	Microsoft Office	Single-use	4084554484123	Consumable	470	FREE
1672	A4tech	Mouse	2862418231869	Asset	471	FREE
1673	Adobe Photoshop	Reusable	5331173552320	Consumable	472	FREE
1674	Windows 7	Single-use	0026983769420	Consumable	473	FREE
1675	Apple	Keyboard	6382909250507	Asset	474	FREE
1676	Apple	Notebook	3112038281805	Asset	475	FREE
1677	Adobe Photoshop	Reusable	2731110532737	Consumable	476	FREE
1678	Windows 7	Single-use	2440262499392	Consumable	477	FREE
1679	Toshiba	Notebook	7120648653611	Asset	478	FREE
1680	Dell	Notebook	1789346580344	Asset	479	FREE
1681	Jetbrains Account	Single-use	9360833851059	Consumable	480	FREE
1682	Jetbrains Account	Single-use	2555725549344	Consumable	481	FREE
1683	Sony	Phone	6639620694616	Asset	482	FREE
1684	Logitech	Switch	8978801073825	Asset	483	FREE
1685	Adobe Photoshop	Reusable	2781188856440	Consumable	484	FREE
1686	A4tech	Router	5723142358000	Asset	485	FREE
1687	Windows 10	Reusable	7896080762920	Consumable	486	FREE
1688	Apple	Switch	3507333824633	Asset	487	FREE
1689	Dell	Router	1004436824376	Asset	488	FREE
1690	Windows 10	Single-use	5557774374165	Consumable	489	FREE
1691	Toshiba	Mouse	4415655053886	Asset	490	FREE
1692	Corsair	Monitor	6053238174931	Asset	491	FREE
1693	Logitech	Switch	5687710332550	Asset	492	FREE
1694	Sony	PC	4648241619696	Asset	493	FREE
1695	Corsair	PC	2993496544975	Asset	494	FREE
1696	Windows 10	Single-use	8440781073770	Consumable	495	FREE
1697	Apple	Notebook	6835614016574	Asset	496	FREE
1698	Jetbrains Account	Reusable	7307994404689	Consumable	497	FREE
1699	A4tech	Mouse	1066128247634	Asset	498	FREE
1700	Toshiba	Monitor	7188553097630	Asset	499	FREE
1701	Toshiba	Router	9772932747230	Asset	500	FREE
1702	Sony	Keyboard	9192452636232	Asset	501	FREE
1703	Dell	Notebook	0766465408040	Asset	502	FREE
1704	Microsoft	PC	4125982180772	Asset	503	FREE
1705	Windows 7	Reusable	1033769179327	Consumable	504	FREE
1706	Adobe Ilustrator	Reusable	0677075889375	Consumable	505	FREE
1707	Logitech	Router	9957468701806	Asset	506	FREE
1708	Windows 10	Single-use	9322162524790	Consumable	507	FREE
1709	Microsoft Office	Reusable	6311768663326	Consumable	508	FREE
1710	Sony	Router	6531346911506	Asset	509	FREE
1711	Youtube Premium	Reusable	3313351276343	Consumable	510	FREE
1712	A4tech	Router	0030222272138	Asset	511	FREE
1713	Youtube Premium	Single-use	4926065973942	Consumable	512	FREE
1714	Toshiba	Keyboard	0188047142034	Asset	513	FREE
1715	Adobe Ilustrator	Reusable	4759397992769	Consumable	514	FREE
1716	Toshiba	Phone	4762659922022	Asset	515	FREE
1717	Microsoft	Mouse	6481098630975	Asset	516	FREE
1718	Adobe Photoshop	Single-use	0058515927879	Consumable	517	FREE
1719	Toshiba	Router	2169893236595	Asset	518	FREE
1720	Jetbrains Account	Single-use	7735792088617	Consumable	519	FREE
1721	MSSQL	Reusable	9750631549089	Consumable	520	FREE
1722	Corsair	Monitor	6330236057399	Asset	521	FREE
1723	MSSQL	Single-use	0004318068691	Consumable	522	FREE
1724	Microsoft	PC	4307816030754	Asset	523	FREE
1725	A4tech	PC	4275310496737	Asset	524	FREE
1726	Microsoft Office	Single-use	5955991585805	Consumable	525	FREE
1727	Windows 10	Single-use	4314890209334	Consumable	526	FREE
1728	Dell	Router	4821633151411	Asset	527	FREE
1729	Corsair	Switch	8811680022495	Asset	528	FREE
1730	A4tech	Monitor	4914132146672	Asset	529	FREE
1731	Logitech	Notebook	7390331483263	Asset	530	FREE
1732	Lenovo	Mouse	3468235908596	Asset	531	FREE
1733	Logitech	Notebook	0944065749859	Asset	532	FREE
1734	Adobe Ilustrator	Single-use	9089772499442	Consumable	533	FREE
1735	Microsoft Office	Reusable	7471958803013	Consumable	534	FREE
1736	Lenovo	Monitor	8835138852168	Asset	535	FREE
1737	Windows 7	Reusable	1503470673822	Consumable	536	FREE
1738	Logitech	Keyboard	4966000586899	Asset	537	FREE
1739	Sony	Keyboard	4227024308981	Asset	538	FREE
1740	Sony	PC	4729875548931	Asset	539	FREE
1741	Sony	Mouse	1574174249231	Asset	540	FREE
1742	Windows 10	Reusable	0945247870514	Consumable	541	FREE
1743	Microsoft	Phone	0021526110783	Asset	542	FREE
1744	Adobe Photoshop	Single-use	2531881906238	Consumable	543	FREE
1745	Youtube Premium	Reusable	2812923610475	Consumable	544	FREE
1746	Microsoft Office	Single-use	4848571211793	Consumable	545	FREE
1747	Windows 10	Single-use	6878973873334	Consumable	546	FREE
1748	Microsoft	PC	1714239006779	Asset	547	FREE
1749	Windows 10	Reusable	3411624865642	Consumable	548	FREE
1750	Logitech	Switch	6551258696773	Asset	549	FREE
1751	Apple	Phone	6045735203832	Asset	550	FREE
1752	Microsoft	Monitor	6772436544048	Asset	551	FREE
1753	Apple	Router	7973121673817	Asset	552	FREE
1754	Adobe Ilustrator	Single-use	5319320927948	Consumable	553	FREE
1755	Sony	Notebook	5272246031567	Asset	554	FREE
1756	Lenovo	Router	0860893422163	Asset	555	FREE
1757	Microsoft	Router	7979101736244	Asset	556	FREE
1758	A4tech	Keyboard	3215088828164	Asset	557	FREE
1759	Corsair	Phone	6454369589203	Asset	558	FREE
1760	Logitech	Monitor	4201738246944	Asset	559	FREE
1761	Sony	Notebook	7939188349614	Asset	560	FREE
1762	A4tech	PC	5251411820526	Asset	561	FREE
1763	Windows 7	Reusable	4847518432635	Consumable	562	FREE
1764	Logitech	Phone	1092990902586	Asset	563	FREE
1765	Lenovo	Switch	3839111772127	Asset	564	FREE
1766	Apple	Router	4873444702504	Asset	565	FREE
1767	Sony	Switch	1698483134658	Asset	566	FREE
1768	Jetbrains Account	Single-use	0333071171585	Consumable	567	FREE
1769	Microsoft Office	Single-use	6102066961166	Consumable	568	FREE
1770	Dell	Router	6813583189925	Asset	569	FREE
1771	Microsoft	Keyboard	7472019661498	Asset	570	FREE
1772	MSSQL	Reusable	9260269605199	Consumable	571	FREE
1773	Adobe Ilustrator	Single-use	6553838722421	Consumable	572	FREE
1774	Windows 10	Single-use	1549726653002	Consumable	573	FREE
1775	Dell	Monitor	3429919190089	Asset	574	FREE
1776	Sony	Notebook	0308773639824	Asset	575	FREE
1777	Windows 7	Single-use	7429259747239	Consumable	576	FREE
1778	Adobe Ilustrator	Reusable	8641093128497	Consumable	577	FREE
1779	Youtube Premium	Single-use	8942692663793	Consumable	578	FREE
1780	Windows 10	Single-use	0883640953888	Consumable	579	FREE
1781	Sony	Keyboard	2749546535987	Asset	580	FREE
1782	Microsoft Office	Reusable	1547823244222	Consumable	581	FREE
1783	Dell	Switch	1120278044373	Asset	582	FREE
1784	Microsoft Office	Single-use	5318851214862	Consumable	583	FREE
1785	Adobe Photoshop	Reusable	9280660890949	Consumable	584	FREE
1786	Microsoft	Monitor	3046814224550	Asset	585	FREE
1787	Windows 10	Single-use	5941875024812	Consumable	586	FREE
1788	Toshiba	Router	5495409561293	Asset	587	FREE
1789	Microsoft	Phone	5523475872873	Asset	588	FREE
1790	Jetbrains Account	Reusable	8520923846544	Consumable	589	FREE
1791	MSSQL	Reusable	8372501604950	Consumable	590	FREE
1792	Jetbrains Account	Single-use	7544812880649	Consumable	591	FREE
1793	Apple	Notebook	1843784483916	Asset	592	FREE
1794	Windows 10	Reusable	1040072532435	Consumable	593	FREE
1795	Dell	Mouse	6181341957087	Asset	594	FREE
1796	Youtube Premium	Reusable	3382708910001	Consumable	595	FREE
1797	MSSQL	Single-use	6913048155394	Consumable	596	FREE
1798	Corsair	Mouse	1006206481192	Asset	597	FREE
1799	Windows 7	Reusable	7603266460477	Consumable	598	FREE
1800	Microsoft Office	Single-use	4410478315975	Consumable	599	FREE
1801	Microsoft	PC	7805488793412	Asset	600	FREE
1802	Dell	Notebook	9482875759992	Asset	601	FREE
1803	Microsoft	Switch	1264923243848	Asset	602	FREE
1804	Adobe Ilustrator	Reusable	4043925528026	Consumable	603	FREE
1805	Windows 7	Single-use	3761814478825	Consumable	603	FREE
1806	Lenovo	Router	8296912185122	Asset	603	FREE
\.


--
-- Data for Name: asset_fault; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.asset_fault (asset_failt_id, fault_id, asset_id, time_of_failure, fix_time, fixable) FROM stdin;
\.


--
-- Data for Name: asset_tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.asset_tag (asset_tag_id, used_tag, used_asset) FROM stdin;
\.


--
-- Data for Name: department; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.department (department_id, department_name, department_location) FROM stdin;
1	department_test	1
454	Terry-Jacobson	1
3	Security	1
455	Dennis-Thomas	3
456	Evans, Bryant and Stewart	3
457	Mcconnell-Morales	4
458	Massey-Wagner	5
459	Myers-Mack	6
460	Miller-Smith	7
461	Graham-Romero	8
462	Schwartz, Castillo and Martin	9
463	Wilson, Ramirez and Harris	10
464	Johnson-Vega	11
465	Huynh, Roberts and Blanchard	12
466	Taylor-Russell	13
467	Phillips Group	14
468	Valdez-Simpson	15
469	Barnett LLC	16
470	Hudson, Santana and Hensley	17
471	Rogers, Smith and Petersen	18
472	Campbell and Sons	19
473	Brown-Campbell	20
474	Phelps, Stevenson and Harrington	21
475	Allen-Warner	22
476	Mejia-Schneider	23
477	Manning, Williams and Zimmerman	24
478	Shannon-Guzman	25
479	Green, Shaw and Davidson	26
480	Thomas Ltd	27
481	Hernandez-Doyle	28
482	Davis-Burns	29
483	Bruce, Garcia and Evans	30
484	Owens and Sons	31
485	Carson, Stanley and Robinson	32
486	Jordan-Johnson	33
487	Johnston Group	34
488	Watson LLC	35
489	Anderson PLC	36
490	Fernandez-Hamilton	37
491	Roberts, Miller and Delgado	38
492	Valencia-Long	39
493	Hill, Irwin and Hamilton	40
494	Mcdonald, Sandoval and Clark	41
495	Henderson-Carter	42
496	Mccarthy-Perez	43
497	Wiggins, Green and Russo	44
498	Dixon, Oconnell and Reid	45
499	Mcintosh, Erickson and Cline	46
500	Wilson and Sons	47
501	Frank, Davidson and Kirk	48
502	Hicks, Rodriguez and Durham	49
503	George-Kennedy	50
504	Lucas-Bailey	51
505	Tate-Clarke	52
506	Bryant-Oneill	53
507	Morgan Inc	54
508	Jensen-Hill	55
509	Lowery-Brown	56
510	Richardson-Brady	57
511	Richards-Brown	58
512	Wilson-Acosta	59
513	Rodriguez LLC	3
514	French PLC	1
515	Gutierrez-Wilkinson	3
516	Johnson-Wagner	3
517	Mccarthy-Salinas	4
518	Taylor Inc	5
519	Brown Inc	6
520	Robertson LLC	7
521	Perez, Dudley and Baldwin	8
522	Schmidt and Sons	9
523	Banks-Reed	10
524	Stewart, Braun and Jacobson	11
525	Robinson PLC	12
526	Franklin-Mitchell	13
527	Stewart and Sons	14
528	Gill, Flores and Webb	15
529	Jones-Cherry	16
530	Lopez, Davis and Garcia	17
531	Hernandez-Mejia	18
532	Edwards, Walker and Smith	19
533	Garcia, Nelson and Crawford	20
534	Davis, Rhodes and Davidson	21
535	Erickson-Brewer	22
536	Foster-Thomas	23
537	Duran PLC	24
538	Perry Inc	25
539	Kennedy, Ferguson and Forbes	26
540	Gutierrez-Davis	27
541	Avila Ltd	28
542	Sullivan-Williams	29
543	Potter PLC	30
544	Sutton-Durham	31
545	Wagner, Taylor and Rivera	32
546	Morgan PLC	33
547	Smith Group	34
548	Conway PLC	35
549	Gonzalez Group	36
550	Stanley PLC	37
551	Maldonado, Moore and Andrade	38
552	Johnson-Fox	39
553	Mendoza, Smith and Reynolds	40
554	Henry, Wu and Parrish	41
555	Brown and Sons	42
556	Washington, Johnson and Abbott	43
557	Thomas-Hart	44
558	Powers PLC	45
559	Gomez and Sons	46
560	Bell PLC	47
561	Olson, Martinez and Wolf	48
562	Hammond-Summers	49
563	Munoz Ltd	50
564	Allison, Cox and Graves	51
565	Delacruz, Graves and Watson	52
566	Lopez, Hodges and Stone	53
567	Brooks, Vazquez and Silva	54
568	Charles-Stafford	55
569	Greene-Rich	56
570	Lindsey, Drake and Davis	57
571	Myers Group	58
572	Gill-Rogers	59
573	Cook, Wilson and Haley	3
574	Bradley-Savage	1
575	Bass, Lamb and Bond	3
576	Bridges Ltd	3
577	Zimmerman Group	4
578	Ellis Inc	5
579	Walsh, Galvan and Jones	6
580	Bond, Levy and Murray	7
581	Butler, Ford and Estrada	8
582	Wilson-Young	9
583	Adkins-Vazquez	10
584	Gill, Hayes and Pham	11
585	Avery and Sons	12
586	Garcia-Hernandez	13
587	Graham Ltd	14
588	Lamb-Green	15
589	Yu-Moran	16
590	Lee, Gonzalez and Bowen	17
591	Myers, David and Romero	18
592	Rogers, Lewis and Bruce	19
593	Williams-Anderson	20
594	Rodriguez-Booth	21
595	Scott, Flores and Wood	22
596	Hall-Benjamin	23
597	Wiley, Carlson and Franklin	24
598	Sullivan, Clark and Holden	25
599	Wright, Sellers and Grant	26
600	Davis, Zavala and Shields	27
601	Gomez-Drake	28
602	Oneill-Perkins	29
603	Oneill-Turner	29
\.


--
-- Data for Name: fault; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fault (fault_id, type_of_fault) FROM stdin;
\.


--
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.location (location_id, state, address, postcode) FROM stdin;
1	slovakia	jarabinkova ba	85106
3	United Kingdom	9726 Barnes Valley	17299
4	Greenland	26741 Jacob Islands	15024
5	Denmark	787 Bianca Crest Suite 804	56393
6	Bhutan	85004 Ramsey Drive	92934
7	Greece	92715 Rush Gardens Apt. 332	20494
8	Korea	188 Christopher Lodge	90123
9	Isle of Man	974 Baker Courts Apt. 799	84339
10	Fiji	1175 Bates Valley	24215
11	Comoros	70190 Becky Underpass	9834
12	Saint Martin	97192 Tyler Ridge	25065
13	Thailand	80500 Rivera Roads	29430
14	Monaco	467 Walker Lock Apt. 096	24126
15	Cape Verde	386 Potter Views	30292
16	Cuba	6845 Diaz Street	28565
17	Peru	3635 Kim Inlet	28128
18	Kuwait	57611 Nguyen Motorway	36984
19	Uzbekistan	535 Meghan Mountains Suite 644	8056
20	Serbia	928 Glenda Ranch Apt. 742	41309
21	Andorra	62059 Ellis Isle	82162
22	Christmas Island	05212 Amanda Street	48367
23	Marshall Islands	487 Christopher Mountains Apt. 700	20373
24	Grenada	628 Chaney Common	7881
25	United States of America	240 Robert Dam	42472
26	Vietnam	21392 Hudson Avenue	50536
27	Mozambique	693 Peterson Spurs Apt. 925	10624
28	Oman	432 Smith Squares Suite 558	96121
29	Egypt	1534 Andrew Lane	75299
30	Turks and Caicos Islands	238 Vargas Manor Apt. 837	88304
31	Spain	908 Hall Plains	68460
32	Rwanda	3387 Wilson Lakes Apt. 074	97773
33	American Samoa	92784 Garrett Stream	80500
34	Guatemala	30346 Keith Course	17508
35	Kyrgyz Republic	390 Phillip Ports Apt. 868	89001
36	Nepal	90383 Theresa Court Apt. 723	26132
37	Cape Verde	50372 Kristine Points	91660
38	Greece	28989 Juan Coves	22467
39	Romania	6338 Shea Prairie	19637
40	Georgia	9550 Joel Junction Suite 454	77327
41	Iraq	734 Corey Avenue Apt. 440	31572
42	Switzerland	57639 Steven Walks	29875
43	South Africa	376 Lauren Square	11845
44	Honduras	18183 Christopher Ferry	53677
45	Palau	713 Walker Rapids	98734
46	Madagascar	5095 Shepherd Plains	36040
47	Peru	8307 Ramirez Park	7509
48	Nicaragua	852 Mitchell Ranch Apt. 685	74223
49	Sierra Leone	15670 Jennifer Prairie Apt. 072	46596
50	Burkina Faso	4017 Price Brook	64527
51	Slovakia (Slovak Republic)	20820 Meyer Plains	98637
52	Uganda	635 Kayla Haven Apt. 938	81604
53	Bhutan	70357 Margaret Mission	39250
54	Lebanon	99470 Michael Plain Apt. 556	78734
55	Netherlands	2084 Andrea Roads	9132
56	Uzbekistan	5669 Blair Vista Apt. 882	93680
57	Dominica	5593 Thomas Mountain Suite 422	85050
58	Korea	043 Donald Fields Suite 501	76030
59	Jamaica	7213 Jacqueline Haven Suite 134	73776
60	French Guiana	54529 Rhonda Meadow Suite 326	8419
61	Azerbaijan	45636 Stevens Lodge	82682
62	Yemen	5798 Cassidy Crossroad	4747
\.


--
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tag (tag_name) FROM stdin;
\.


--
-- Data for Name: ticket; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ticket (invoice_id, time_created, time_accepted, time_assigned, user_info, asset_info, comment) FROM stdin;
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (user_id, first_name, surname, city, address, postcode, user_department, login, password, is_admin) FROM stdin;
4161	Ryan	Owens	East Jenny	567 Christian Squares Apt. 703	80658	456	nathaniellee	8!T3ETYh^f	t
4162	Megan	Owen	Port Jason	9706 Andrea Lights Apt. 721	49555	457	ustrickland	K!6$WrEH%I	f
4163	Jeremy	Cohen	Schultzview	549 Lowe Viaduct	28386	458	gcervantes	!0RSlw2UgJ	f
4164	Pamela	Smith	South Cherylfort	344 Smith Village Apt. 223	86333	459	deanjerry	Ve0nB$Rz*R	f
4165	Matthew	Mcclain	Port Lisastad	038 Sanchez Estates	97237	460	ulambert	%92yDtV9YQ	f
4166	Taylor	Wright	Port Matthew	085 Samuel Shoal	86204	461	nwallace	@YB99nOog!	f
4167	Ricky	Davis	East Ashley	581 Jones Flats Apt. 391	40309	462	timothytaylor	E$dbIDpO!1	f
4168	John	Smith	Smithport	2136 Dawson Prairie Apt. 041	37934	463	nfields	$m6v*OXqv)	f
4169	Jeremy	Jordan	Lake Brian	401 Cindy Coves Suite 046	89127	464	farleymichelle	(1!PFwZw9U	f
4170	Laura	Pierce	Taylorbury	83778 Harrison Shore Suite 287	78623	465	taylor47	^6YJkr5RLV	f
4171	Matthew	Donovan	Hallland	70457 Peterson Row Apt. 339	60751	466	victoriathompson	@UWJEThw_8	f
4172	Kyle	Stark	Alvarezbury	158 Stephanie Freeway	93215	467	rebecca76	#14KWIg8!F	f
4173	Susan	Johnson	Robersonville	837 Roach Run	21032	468	rebecca22	$9o&@YjTIm	f
4174	Bruce	Malone	Torresborough	84699 Lynch Parkway	8277	469	veronica05	&%mQ7bYn*N	f
4175	David	Meyer	East Briannaland	8643 Hunt Isle Suite 459	89072	470	robert81	6_8JL5Qhjn	t
4176	Sherry	Greer	East Kristin	7218 Mary Skyway Suite 952	89075	471	michael78	)_VXWoAc3n	f
4177	Sean	Delgado	Keithville	6277 Rivera Fords	90454	472	suzanne50	%#0ZQXrZ9k	f
4178	Timothy	Knight	Edwardfurt	88816 George Plains Apt. 308	48249	473	jonescarolyn	(BPofdQtb2	f
4179	Kenneth	Hines	Ralphborough	835 Smith Mountains	5811	474	sonya09	q9B_eeWW(6	f
4180	Alison	Acosta	West Frank	54023 Sarah Mews Apt. 500	29985	475	dana92	$T0yTp($M5	f
4181	Kayla	Long	South Ashley	64055 Samuel Keys	54309	476	leonardwebb	ksvZkUB3@9	f
4182	Ann	Odonnell	Riveraburgh	77585 Griffin Springs	4075	477	jonathan63	6pYuNe^N!9	f
4183	Brandon	Williams	Brittanyshire	64624 Timothy Streets Apt. 476	45493	478	jasonheath	+1wQ5QhbxV	f
4184	Jake	Taylor	Keithfurt	966 Stafford Lights	63104	479	joshua28	_ZDcwldjr6	f
4185	Anthony	Graham	Davismouth	64088 Long Field	67488	480	elizabethball	@AU@e(W0r6	f
4186	Sandra	Ayala	Port Colleenshire	55815 Cochran Plaza Apt. 536	65641	481	roberto31	ot3UWw@!+B	f
4187	Michael	Johnson	Rebeccastad	167 Garcia Keys Suite 247	43094	482	kelseybaker	^ehayaUt4S	f
4188	Cheryl	Jones	Danielburgh	976 Craig Lakes Apt. 468	12130	483	deborahgeorge	09E58Lxn*V	f
4189	Melissa	Jimenez	Debramouth	2723 Andrew Forges Apt. 625	54932	484	kbishop	v%J9GIdUq1	f
4190	Lisa	Silva	Port Joshua	442 Christine Port Suite 214	53263	485	ythompson	#I(G8l!l#5	f
4191	Shawn	Montgomery	Mitchellstad	0543 Brandy Cliffs Suite 776	34627	486	tammy84	&&A2UIkb5w	f
4192	Hunter	Montoya	South Staceyport	75660 Peters Inlet	45582	487	gibbsbecky	$cg#Ty!P!6	f
4193	Michael	Smith	New Jeffreyside	9587 Nelson Freeway Suite 966	93844	488	stephanie82	W_UEka2k@4	f
4194	Nicole	Thomas	Francoshire	51021 Gregory Grove Apt. 828	71203	489	qward	d(6VVVkpW&	f
4195	Denise	Jackson	Valerieside	68460 Terri Road	63149	490	alexander47	Q6p_eaDv(f	f
4196	Chelsea	Atkins	Lowehaven	953 Zimmerman Forges Apt. 500	74598	491	michael72	1&CFRc(d_2	f
4197	Alexander	Nelson	Clementsshire	7148 Emily Lakes Suite 299	56145	492	wthomas	K27Jq@obr^	f
4198	Brandon	Peters	Lake Christopherland	15285 Welch Walk	95189	493	matthew84	u2uFl+&R!m	t
4199	Steven	Frye	New Scottville	22674 Dalton Ramp	54725	494	davisduane	&2QxH+8sx@	t
4200	Richard	Sanchez	Petersonfurt	884 Michael Isle Suite 958	17978	495	foxdonald	MFP_9uQsR%	f
4201	Matthew	Benson	Calvinfort	6732 Clark Pine	43264	496	rayrobert	Y6*0H29l%m	f
4202	Ronald	Bryant	North Matthewton	5879 Dawn Row Apt. 913	17081	497	kristopher50	AJe*6H@eg#	f
4203	Shawn	Matthews	Whitestad	4869 Garcia Crescent Apt. 515	1264	498	johnsonnathan	_Z8W03oF!o	f
4204	Jessica	James	Matthewside	414 Nelson Ways	66878	499	vgarner	RIU96Ds6%9	f
4205	Leslie	Lewis	Gomezland	002 Ashley Streets Suite 870	60588	500	anthony77	e9QIjpBr%0	f
4206	Joshua	Butler	North Angela	43436 Clarke Shoals Suite 039	56596	501	raymond93	*fv*3L&eOC	f
4207	Zachary	Mitchell	Bakerchester	536 Roberts Pines Apt. 746	36917	502	nicole04	gPi3KTKdI)	f
4208	Jose	Bradshaw	New Amanda	35929 Cruz Bypass	85177	503	amyward	s6cKR2cr!I	t
4209	Kelly	Hernandez	Matthewport	7967 Tiffany Heights Suite 963	38172	504	uwhite	cH8Fove)&X	f
4210	Audrey	Kelly	Lake Kimberly	47969 Jasmine Track	1875	505	jeremymcdowell	%@9VXRm@CI	f
4211	Trevor	Harper	West Deniseberg	4078 Juan Island Suite 788	88766	506	xwall	_BV0pKAynX	f
4212	Barbara	Randolph	South Kylehaven	3433 Shelton Streets	68579	507	idaniels	+8OOsGgvch	f
4213	Jasmine	James	Chavezton	4266 Jerry Locks	58854	508	thompsonwilliam	+09!6N$xs8	t
4214	William	Ho	Mooreton	2095 Andrea Villages Suite 051	53025	509	laura57	@K&g9TJqt1	f
4215	Mario	Hernandez	Williamville	6665 Laura Shores Suite 041	81397	510	douglasdonald	Ni5PzdGxT!	f
4216	Scott	Velazquez	East Kathleenmouth	33181 John Expressway Apt. 552	87328	511	kristen21	v2aSFeUS&F	f
4217	Caitlin	Thomas	Lake Markshire	98127 Mason Viaduct	76684	512	christopher91	%42aJqetIu	f
4218	Cody	Wheeler	Hudsonfurt	46377 Patricia Path Suite 574	81783	513	christopher53	9f6U!4amt*	f
4219	Austin	Harris	New Kevin	76800 Paul Roads	80640	514	gramirez	KOj0KyDv!5	f
4220	Joseph	Baldwin	Matthewbury	24144 Reynolds Plaza Apt. 623	89822	515	dannyrice	8%lGOg(p^u	f
4221	Robin	Bates	Christinaberg	14947 Paul Drives Apt. 889	81864	516	iclark	*@EXHfvVi0	f
4222	Kevin	Young	Vasquezborough	619 Reyes Shoal Suite 977	12642	517	cynthia08	w8Vb&PaJ+o	f
4223	Christina	Mendoza	South Brianstad	493 James Drive	44526	518	dfrazier	2WxWqNpi%A	f
4224	Kayla	Mendez	Sheltonville	193 Wilson Springs	84030	519	david03	s$99G0Zxr&	t
4225	John	Hamilton	Wilsonfurt	273 Hill Field Apt. 657	19818	520	matthew45	6!Pq9LxKy2	f
4226	Pamela	Morris	East James	321 Christopher Parkway Apt. 965	12057	521	johnsonmichelle	H644p5Xc*I	f
4227	Briana	Buckley	Drakeside	901 Schwartz Extensions	6532	522	amandakelly	hX8KsBMl(%	f
4228	Virginia	Joseph	Collinsport	09812 Hawkins Circles Suite 854	22908	523	katherine38	#uVd_1DaP8	f
4229	Angela	Duke	Stephaniehaven	23849 Shelly Prairie	70843	524	santoskyle	D9RHNhgF$^	f
4230	Linda	Sanchez	Milestown	752 Kelly Lodge	27939	525	james64	%btj7QzJS0	f
4231	Andrew	West	Lake Robertmouth	02442 Baker Keys	57809	526	jasonhenderson	y5AHsnE&@j	f
4232	Emily	Nguyen	Kelseyshire	40430 Holland Flat	77179	527	loriblevins	0^7BCF8nhi	f
4233	Nicole	Flores	Jenniferchester	0809 Christensen Fort	89250	528	daniel04	s&47KKZmDc	f
4234	Bethany	Short	Odonnellville	0404 Gonzalez River	74923	529	blairtonya	+00#WiMGp^	f
4235	Hannah	Rodriguez	Lake Anthony	88098 Patton Creek Suite 784	27022	530	kcannon	*V)AcPrx8F	f
4236	Cynthia	Kerr	South Kylefort	4969 Gonzales Green Apt. 445	87293	531	daniel16	9KFMJyHf(l	t
4237	Amy	Hayes	West Richard	2276 Swanson Land Suite 956	2252	532	michael15	8*#0Wyjh0T	f
4238	Anthony	Henry	Quinnhaven	825 Allen Gardens	81287	533	smallmichelle	b*&O8CbpU0	f
4239	Christian	Campbell	Markberg	640 Christopher Plains	51110	534	beasleyterri	NhwJHdKa+3	f
4240	Jason	Jensen	South Dominique	894 Christopher Wall Suite 037	51405	535	jose01	#GAAvrMcW4	f
4241	Laura	Rivera	Lake Nicolefort	8285 William Point Suite 970	14217	536	fostersamantha	&PNn8I#nFL	f
4242	Barbara	Mcclain	South Curtis	10127 Russell Trail	94958	537	joe92	Q7R6AFde_5	f
4243	Anthony	Zamora	Robinsonmouth	08202 Sherry Falls	49208	538	angie52	H2BiLhwu(r	f
4244	Alexandra	Fowler	Kyliemouth	42488 Tiffany Cliffs Apt. 961	57010	539	vjohnson	^K2lOYMez(	t
4245	Kim	Mitchell	New Jaredmouth	888 Strickland Locks	33699	540	martinsean	)s2H4B3k2#	f
4246	Elizabeth	Collins	South Miranda	355 Jason Stream Suite 368	33188	541	wsmith	A+1LIuNlTb	f
4247	Melissa	Williams	Timothyland	4968 Patrick Spring	76022	542	dunntonya	*R8DqUcH02	f
4248	Rachel	Burgess	Rivasside	776 Laurie Centers	39486	543	aaron71	WV3ioQ3gR*	f
4249	Courtney	Osborn	New Tyler	879 Amber Mills	68962	544	antoniokelly	s#f9QidI)3	f
4250	Darlene	Shah	East Renee	488 Shawn Via	54364	545	ewatson	0K!ZpW%H%3	f
4251	Joseph	Mccoy	Sandershaven	3076 Carter Mill	25208	546	melissakim	h8$o%7TK@f	f
4252	Tiffany	Garcia	Port Russell	2083 Hanson Crossroad Suite 937	63428	547	rodriguezmatthew	$+#L1Vx@xe	f
4253	Lori	Adams	North Kristenstad	2268 Franklin Crossing Suite 590	40262	548	andre26	#PFy8BHhd2	f
4254	Jesse	Gentry	West Rachel	614 Carol Unions Suite 452	56914	549	andersonronnie	*xU9bmHnf3	t
4255	David	Henry	Jeffersonbury	634 Daniel Walks	3600	550	moorejennifer	Qu9LgcOWA_	f
4256	Robert	Brown	Whiteside	3905 Adam Mill	55470	551	manuel54	(h!FEYKo54	f
4257	Vicki	Moore	Whiteborough	69262 Larson Trail Apt. 407	79436	552	lmoore	j6DigYbw@u	f
4258	Michael	Tran	Howardberg	62887 Kennedy Mountain	77483	553	alexis30	+E2oSYrb2f	t
4259	Marcia	Lamb	Margaretborough	2863 Norton Knolls	72039	554	reynoldstravis	Z^2geIbxm5	f
4260	Renee	Neal	East Kayla	640 Perez Corners Apt. 013	41206	555	jasonmitchell	2S7Ao9di2!	f
4261	Elizabeth	Kim	Shannonville	525 Michael Plaza Apt. 234	42965	556	cheryl72	d9N+WiD&&0	f
4262	Hannah	Durham	Port Margaretmouth	0452 Holt Drive	11561	557	hhernandez	%f7aYhzI^+	f
4263	Laura	Hall	Fernandezport	31911 Daniel Gateway Suite 806	4215	558	ssharp	7bgpMW_i^n	f
4264	Willie	Foster	Powellland	57134 Cook Rue Suite 133	65297	559	tdelgado	&8xRC!DspN	f
4265	Thomas	Smith	Lake Autumn	14660 Herman Freeway	48332	560	pachecolinda	eMAph!vy!2	f
4266	Sean	Tanner	Barbarafurt	65318 Brian Radial	68162	561	thomasbarrera	(C9uUHkR@F	f
4267	Donald	Osborn	Washingtonmouth	13829 Aguirre Fork Apt. 980	25454	562	singhbrenda	%3Ger9lg%K	f
4268	David	Crawford	Kingtown	2345 Madison Passage Suite 140	13308	563	xwinters	E$$f7Dns^0	f
4269	David	Palmer	Cruzstad	59414 Anthony Tunnel	74048	564	lestermichael	*_9SdobeJT	f
4270	Heidi	Brown	Lake Julie	605 Krystal Hills Suite 195	42493	565	smithheather	GGN8c5Lw%%	f
4271	Justin	Barber	New Dale	661 Robert Mountain	48443	566	harrisonkaren	B)4BZQiUmM	f
4272	Cindy	Schmidt	West Zacharyberg	056 Rhonda Ports	46269	567	ijones	$H8MQyAXn&	f
4273	Alexander	Knapp	Jonesside	591 White Summit Apt. 126	63760	568	bjames	o$jBNu&4*6	f
4274	Wendy	Lowe	Port Matthewtown	4901 Cunningham Mountains	42257	569	xcox	@GwLC@^x0b	f
4275	Kevin	Merritt	Port Samuelmouth	9011 Natasha Valleys Suite 811	51817	570	friedmanvictoria	$f8jm+Hf10	f
4276	Tina	Romero	Lake Sarahshire	54801 Thomas Curve Suite 708	41469	571	shawn72	cza3Y!ApJ)	t
4277	Virginia	Harris	South Tracybury	44737 Jennifer Port Apt. 936	39085	572	kjimenez	*gPH)^)p5+	f
4278	Andrew	Jimenez	South Matthew	58719 Desiree Run Apt. 620	53308	573	weisseric	(1l&5Odj96	f
4279	Danielle	Perry	Ruthfort	224 Patricia Freeway	40258	574	palmerlaura	#0nIH@Ah+Y	f
4280	Brandi	Nixon	North Jenniferchester	59312 Julia Valley Apt. 113	61163	575	amywalker	ZuHYqDJG)3	f
4281	Michael	Steele	Marciaberg	58831 Humphrey Pike Suite 766	69514	576	jodyhester	EQM0JQcHm*	f
4282	Meghan	Fitzgerald	Katherinebury	358 Morris Lakes	1734	577	hannah50	)F6JZxW2!$	f
4283	Kelly	Reilly	Jillport	610 Megan Island	60900	578	agutierrez	6V74Xp5D!P	f
4284	Judith	Sanchez	Paigebury	089 Aaron Ferry Apt. 886	83712	579	fkeith	^e4(REWjxt	f
4285	Stephanie	Stephens	Clarkstad	32259 Denise Point	48394	580	rcarroll	9k$40N(n6!	f
4286	Kurt	Gamble	Brianmouth	6007 Amy Spring Apt. 157	23753	581	troygallagher	)3oiNKNl5c	f
4287	Alicia	Tucker	East Natalie	081 Cardenas Greens Apt. 153	76033	582	jonesjohn	)1ARl1AJ$*	f
4288	Gary	Lawrence	Jennaburgh	48373 Maria Avenue	1400	583	david80	)672oUpwq*	f
4289	Judy	Johnson	Samuelfort	342 Callahan Grove	76220	584	richardaustin	X4NEYv8l#D	f
4290	Kelly	Norton	Nathanfort	84621 Sanchez Light Suite 844	65816	585	sdavis	&OmU5N+l$(	f
4291	Lori	Green	Sheilaside	1436 Colleen Plains	20173	586	amberelliott	K)6hlQt3fO	f
4292	Lisa	Wilson	West Amanda	0392 Grant Mountain	74819	587	larsenkathryn	N6V$6RrxFH	f
4293	Kyle	Dunlap	Bakerside	13817 Brenda Mall Apt. 888	82165	588	larry62	N)f84PDf&B	f
4294	Dawn	Porter	Wandaland	887 Rebecca Ford	97591	589	gracehughes	!w7fANgGjf	f
4295	Molly	Cook	Lake Alyssa	594 Irwin Ridges Suite 918	19499	590	wsoto	0)0AsWEx(C	f
4296	Justin	Shea	Lake Scottborough	33343 Williams Village	53314	591	nboyd	^3S$aGtVx*	f
4297	Frank	Collins	West Angela	67895 Julian Crest Apt. 310	66577	592	brownashley	OIVcY7DuI_	f
4298	Corey	Haney	East Aaronport	632 Russell Harbors	3167	593	lindalopez	n2NGDjov)h	f
4299	Stephanie	Williams	East Andrew	48646 Miller Ramp	3586	594	scottcastillo	^08Gsk90$F	f
4300	Diana	Lam	Griffinberg	193 Myers Circles Suite 578	81151	595	gamblenathan	!4QF2Ol!+b	f
4301	Thomas	Hall	Lake Brian	688 Jackson Plains	51512	596	victoriaharris	V41iR0K*#q	f
4302	Samantha	Adams	Lake Carolyn	4027 Williams Viaduct Apt. 781	83075	597	klopez	S3&Q9VMash	f
4303	Jonathan	Torres	New Kathyburgh	6491 Cruz Springs Apt. 107	50621	598	cadams	H2&Z%6tf^n	f
4304	Teresa	Williams	West Dustin	492 Gonzalez Mountains Apt. 884	53870	599	matthew86	@JlOS3Wn7T	t
4305	Rebecca	Vega	West Davidfort	0038 Danielle Forest Apt. 196	26620	600	holly43	12fj3TAo(H	f
4306	Michael	Smith	New Stephanie	60955 Bird Flats Suite 448	37957	601	matthewmitchell	@6WJ&l8^0C	f
4307	Angela	Noble	Dunnland	97832 Melissa Lights	89061	602	brandonstewart	(FSUh0Ofb2	f
4308	Crystal	Davis	Douglasland	55527 Russell Lakes	35938	603	angelaholmes	$3OG53#g%q	f
4309	Donald	Reyes	Andersonburgh	5663 Medina Locks	82655	603	jenniferrowe	&%#4hRuWeA	f
4310	Matthew	Wagner	Willisstad	9913 Dawn Brook	60333	455	melanie56	)2Vr(CiK%5	f
4311	Jennifer	Johnson	Jimchester	2895 Hernandez Ranch	6721	456	mitchellevan	cW&BGtTr&0	f
4312	Alyssa	Thornton	Deborahmouth	67443 Diana Green	86090	457	michelle83	P$4o!jNkkV	f
4313	Brian	Landry	Victoriachester	27338 Thomas Fields	42318	458	veronicagarcia	1FS0^rmt_u	f
4314	Renee	Nelson	New Frank	52606 Howell Lights Suite 540	4959	459	henrycarla	^5pJrA+qWv	f
4315	David	Gaines	South Peter	629 Mary Mountains Apt. 303	20485	460	millernicholas	Nf9JqdSo(S	f
4316	Julie	Dunn	Silvaside	11053 Samuel Courts	6206	461	hillsean	+q7M%Kgb(^	f
4317	Shannon	Martin	West Patricia	54286 Wright Turnpike	85056	462	christian71	8mEW+YYp+7	f
4318	Angela	Lewis	Hudsonstad	748 Douglas Bypass	18440	463	herreraamy	A%X5yKTfXe	f
4319	Robin	Leach	Lindsayfurt	220 Trevor Vista Suite 624	71686	464	kingkristen	+)X8YHJeLZ	f
4320	James	Fuller	Vanessaside	11074 Davis Drives Suite 295	21803	465	tyler70	Kj@0GYwz%0	f
4321	Amber	Williams	Lake Meghanberg	7150 Smith Port	60312	466	hcole	+WUH_kzO(6	f
4322	Jeffrey	Chambers	Lake Brookeville	152 Robert Brooks Suite 441	98756	467	keith58	*b@RhQDi&4	f
4323	Jacob	Henderson	Medinaville	2770 Lisa Row	92613	468	brandongray	*2FX4O&zGG	f
4324	Juan	Pham	New Christopherborough	647 Humphrey Junction	55166	469	sarahwiley	fU*22BQyWI	f
4325	Tyler	Roth	Michellemouth	08838 Fields Crossing Suite 450	92716	470	robertspamela	J3%7PrqW6@	f
4326	Allen	Contreras	New Cynthia	13885 Sarah Loop Apt. 521	20632	471	edward14	4_)2CvoPEo	f
4327	Melissa	Villanueva	New Dawnberg	955 Mcdaniel Crossroad	94960	472	mary25	6M4C3fwh$m	f
4328	George	Foster	South Allenfurt	871 Chandler Branch	79887	473	xmorris	%tjSBUGgW3	f
4329	Daniel	Castillo	East Maryberg	033 Hodges Spur	57159	474	desiree20	E*6T1URr(u	f
4330	Mario	Sanchez	Lake Nicholasbury	2534 White Bypass Apt. 201	5001	475	fsmith	Yb8PsAqm9%	f
4331	Justin	Campbell	New Carl	771 Nicole Route	60134	476	taylorstark	3XSquaWv%F	f
4332	Julie	Flores	Port Melissaton	18657 Emma Knolls Suite 522	84027	477	fergusonchristopher	lb6LNvR4H$	f
4333	Vincent	Boyd	Parrishland	265 Patterson Roads	38721	478	williamsalazar	(h2iIoTPg0	f
4334	Bruce	Anderson	South Roseshire	4453 Rios Village Suite 143	85001	479	fcurtis	F^7C36XxBV	f
4335	Ashley	Erickson	West Mary	3458 Estrada Summit	48460	480	robert51	j8hL4vDV^f	f
4336	Jennifer	Cowan	Jeffreyberg	27574 Pamela Brook Suite 091	81524	481	zimmermanashley	7V3WVyvz)c	f
4337	David	Cole	New Johnport	22111 Frazier Extensions	54307	482	masseyalexandria	Yk_9Ecn2_U	f
4338	Kathleen	Ward	Wyattmouth	46198 Ashley Drive Suite 382	47045	483	ggreene	b3GIMnmR*^	t
4339	Vickie	Rice	East Lisa	983 Sherry Lodge	56407	484	danielhernandez	%dPe#atp+8	f
4340	Thomas	Wilson	Josephburgh	10688 Duffy Land Suite 219	96895	485	lucas81	y1&Y4HkS*)	f
4341	Amber	Perez	Tammyfort	2390 Smith Cove	41532	486	fnoble	4EIgfgIt)#	f
4342	Nicholas	Perez	Pollardshire	24021 Matthew Brook	54035	487	scott50	7o3PKDKf&!	f
4343	Michael	Donaldson	East Jeremyport	07286 Oconnell Drive Apt. 164	37227	488	ryan35	h^2Lv6!N6n	f
4344	Robert	Wong	Port Krista	15654 Bill Freeway	97578	489	charles05	(LTemoKo8#	f
4345	Andrew	Villa	Matthewport	30952 Christopher Branch Apt. 390	54072	490	marcogarcia	FM5N%rbHv!	f
4346	Kimberly	Burton	Port Madisonshire	6141 Frank Viaduct Suite 709	5133	491	tapiadrew	Wb^5AlAMED	f
4347	Allison	Weber	West Travis	9727 Sandoval Trafficway	62836	492	tammythompson	@mjQLEaVp7	f
4348	Robert	Campbell	Maryville	7221 Thomas Route Apt. 512	26909	493	jessicagonzalez	7+#30Nujc^	f
4349	Sean	Hobbs	Bryceburgh	1489 Aaron Walks	46642	494	michaelgraham	^u^W$$7K3A	f
4350	Jenna	James	Lake Kevinbury	3006 Richard Overpass Apt. 737	67293	495	coffeyjessica	#s7SAlUj7a	f
4351	Crystal	Washington	Frankburgh	7245 Jennifer Shores	96188	496	freemanronnie	&1JJ7Kd_@B	f
4352	Stephen	Alvarez	Jamesshire	0579 Johnson Extensions	8346	497	matthew62	NAp%98Kvyb	f
4353	Brandon	Gray	Brownstad	9817 Bonilla Centers Apt. 852	47100	498	idavis	YPSzcdrb#0	f
4354	Edward	Scott	Port John	08300 Phillips Spring	89262	499	samanthasalazar	51LaSS(n#f	f
4355	Lisa	Russo	South Lindafort	725 Murray Fork Suite 060	73302	500	manningjennifer	DaF(6RmtdU	f
4356	John	Carey	New Danielport	305 Salazar Summit	39675	501	shawnkaiser	c4*j@*Il(M	f
4357	Jesse	Burns	Charleshaven	82776 Tucker Green	47152	502	bakersusan	H+5Y@m@AJ6	f
4358	Gary	Lee	Michaelhaven	790 Deanna Radial Apt. 386	26281	503	nielsenmichael	F)j2*Laj0p	f
4359	Valerie	Brown	North Caroline	1552 Sharon Radial	49222	504	ashley95	PFd&3GQvXO	f
4360	Justin	Cox	Port Amandaton	034 Mark Summit	61829	505	amanda55	m%5raDywQF	f
4361	Brian	Hensley	Catherineton	15268 Hunt Dale Apt. 717	4253	506	laurenreid	)Sk0Fzjcun	f
4362	Damon	Lee	Davismouth	2864 Holden Burg Suite 558	29698	507	kevinward	HmqFnh0e!3	f
4363	Cameron	Alvarez	Kristihaven	452 Rebecca Walks Apt. 256	53869	508	kaitlynnelson	%JLGmOeac1	f
4364	Angela	Moore	Troyhaven	146 Anderson Squares Apt. 166	84799	509	shawnmartin	^h$RA(pnV5	f
4365	Kathy	Sexton	Sparkston	826 Thomas Flats	26559	510	brendamiddleton	0SbvRnYx+v	f
4366	Heather	Sanders	Dixonfurt	90417 Brittany Ranch	17902	511	patricia23	1n&rWpb*$P	f
4367	Roberto	Lawson	North Erik	73508 Blanchard Mountain	97836	512	katherineking	K*O0UbpC*C	f
4368	Debra	Patrick	Lake Robert	221 Patrick Plaza Apt. 947	28255	513	tylerthompson	qb4IZXh(t(	f
4369	Jennifer	Matthews	Joshuaside	484 Miguel Isle Apt. 413	12758	514	melissalee	YBcC9Bjy%9	f
4370	Kaylee	Castillo	Ernestmouth	76161 Brian Springs Suite 460	78768	515	tiffanygrant	@4b9QqJ@aU	f
4371	Michael	Jimenez	Port Seantown	5686 Denise Glen	59927	516	wlopez	@q)BdQm+y0	f
4372	Brandon	Salazar	West Steventown	4372 Sanders Trace Apt. 801	39524	517	lharris	O7OiPYlt!7	f
4373	Jeanette	Khan	Tuckerhaven	3672 Stephen Glens Suite 177	81861	518	parrishjason	@9CHM&)Wlh	f
4374	Shannon	Miller	Joannfort	349 Veronica Spur	65873	519	kflores	q**34cERlu	f
4375	Carrie	Cooper	Burnschester	07646 Patrick Shoal Apt. 962	11805	520	leslie85	8N^n5QMh*#	f
4376	Melissa	Thompson	Ericaport	22320 Janice Knoll	64839	521	gonzalezbradley	32pEhi&R%i	f
4377	Darlene	Burke	Michaeltown	807 Mackenzie Causeway Suite 495	2721	522	tina69	*b4UNayW98	f
4378	Jay	Martinez	North Melanieborough	577 Ruiz Harbor Suite 150	72524	523	iturner	@5&OQICE*g	f
4379	Jeffrey	Perez	Port Christinachester	845 Garcia Terrace	59769	524	arthuradams	R!3NuRrfF#	f
4380	Wayne	Ochoa	East Juliaburgh	17869 Warner Plaza Apt. 957	10135	525	cfowler	(7Bw+*gvBy	f
4381	Jane	George	Ortegastad	004 James Inlet	30568	526	mckenziekenneth	L#xbV6OtK4	t
4382	Angela	Mullen	Spencerland	864 Morgan Way Suite 927	86479	527	amy07	0nCuFer_+4	f
4383	Melissa	Adams	New Christyberg	93123 Salazar Walks Apt. 292	64208	528	romerokendra	JhYB4D*mn^	f
4384	Justin	Wu	Juliechester	37143 Eric Viaduct	95593	529	alicia47	@%^00xPgua	f
4385	Danielle	Moore	Robertborough	92271 Derek Plaza Suite 741	56786	530	melissajackson	5OdZIuTD@L	f
4386	Randy	Chaney	Lake Kimberly	463 Summer Lodge Apt. 488	6268	531	fieldspatricia	6a)C5Cno*t	f
4387	Dalton	Greene	Lake Timothy	93380 Fitzpatrick Garden	93415	532	jessica54	v7(T5$ZwC_	f
4388	Gary	Webb	Erikmouth	607 King Overpass Suite 953	93078	533	csmith	#A9Tafb&G)	f
4389	Crystal	Smith	Port Amanda	449 Young Ferry Suite 929	38971	534	sramos	1n6l8GjhT&	f
4390	Christine	Gill	Arianashire	355 Smith Island Suite 501	20163	535	burkechristopher	5mC3bo+m*t	f
4391	Jon	Burgess	New Eileen	117 Joseph Mountain Apt. 157	80633	536	padillajacqueline	@v_QfD2Z$3	f
4392	Linda	Smith	Kayleeville	75026 Martin Course Suite 144	99767	537	baileyrebecca	+)xNq6JdJ(	f
4393	Eric	Miller	Dyerhaven	249 Megan Tunnel	37347	538	oliviapeterson	6$kZ%btp^8	t
4394	Ashley	Salazar	Montgomeryland	13443 Jon Highway	68984	539	justinchen	)^9DXHtMLo	f
4395	Christine	Zimmerman	Donnabury	30703 Mann Lodge	12356	540	molly81	)C9bkAJj%7	f
4396	Diana	Schmidt	New Patriciaton	574 Robinson Loaf	61069	541	justin43	4!1FqGjVTd	f
4397	Melanie	Wilson	Valerieside	039 Richard Summit	31818	542	pricekimberly	V&3uJfDzP3	f
4398	Lindsay	Nguyen	Port Michael	1179 Smith Vista	24600	543	shanebaldwin	!%VSVq^N2m	f
4399	Amber	Patterson	Terranceshire	12631 Rodriguez Cliffs	18178	544	katelynmartin	a+Y0Gnw3Lp	f
4400	Oscar	Wilson	West Elijahmouth	15736 Kevin Court Apt. 529	81990	545	catherine38	D!V2z1Mbbq	f
4401	Rachel	Holmes	Davidberg	88464 Brooks Rapid Apt. 707	74330	546	cookanne	#qu2BL)chx	f
4402	Carlos	Mitchell	Mclaughlintown	538 Anthony Summit	72256	547	craigcohen	r^2RJ$GWji	f
4403	Tiffany	Hicks	Jessicamouth	8464 Megan Cape Apt. 638	33983	548	leachgarrett	l(4OD5X%um	f
4404	Jacob	Reyes	Zhangton	460 Mcgee Ferry Suite 174	88723	549	jeremygeorge	*PQUaQ0uw9	f
4405	Anthony	Jordan	Jennaborough	22436 Johnson Fork Apt. 473	36027	550	zwhite	!1L%Iixyd!	f
4406	Brian	Butler	Jennyshire	8066 Abbott Center Suite 855	51353	551	dianegeorge	SD!^4Dcee)	f
4407	Gary	Williamson	North Alejandromouth	1821 Maxwell Corner Suite 240	51599	552	smithdavid	+vTn(_Gl_2	f
4408	Michael	Howard	Vernonfort	29612 Jason Mall	31259	553	yfrazier	#rSe7PNzd0	f
4409	Timothy	Chavez	New Raymondburgh	021 Moore Stravenue	9636	554	connor92	v79K2c+4U^	f
4410	Timothy	Willis	Lake Hollyville	3533 Sonia Glen	85781	555	ccarter	(zSXwcbK43	f
4411	Carl	Wilcox	Paulshire	0542 Wilkerson Courts Suite 987	3631	556	john59	W#8)Rq8bnU	f
4412	Kristopher	Arnold	Miastad	005 Flowers Station	783	557	patricksalinas	VfUT2Y$t1_	f
4413	John	Garcia	Andrewbury	7539 Christy Burgs Apt. 072	88414	558	zpope	!%&8Umj!v&	f
4414	Dennis	Torres	West Michaelton	612 Bradley Groves Suite 920	32404	559	melindajordan	&0YiLLAz8N	f
4415	Robert	Turner	Stefanieburgh	689 Silva Plaza	16791	560	danielgriffith	&BF0u9VyX&	f
4416	Christopher	Moreno	Lake Matthew	677 Thomas Roads Suite 929	72528	561	mary53	c842PNoC@w	f
4417	Amanda	Burns	East Malik	9699 Gallegos Prairie Apt. 575	82254	562	william77	ZBp4yXf1)X	f
4418	Daniel	Arnold	Sherryhaven	114 Perry Pine	37658	563	taylorevans	k0lXSPfh^@	f
4419	Peter	Morris	Cindyhaven	85675 Murphy Vista	14945	564	karen94	y4Nrdr*2)@	f
4420	Michelle	Rivera	Nicoletown	75073 Tracy Gardens	84211	564	dpratt	ob8G^in3@&	f
\.


--
-- Name: asset_asset_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asset_asset_id_seq', 1806, true);


--
-- Name: asset_fault_asset_failt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asset_fault_asset_failt_id_seq', 1, false);


--
-- Name: asset_tag_asset_tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.asset_tag_asset_tag_id_seq', 1, false);


--
-- Name: department_department_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.department_department_id_seq', 603, true);


--
-- Name: fault_fault_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fault_fault_id_seq', 1, false);


--
-- Name: location_location_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.location_location_id_seq', 62, true);


--
-- Name: ticket_invoice_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ticket_invoice_id_seq', 1, false);


--
-- Name: user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_user_id_seq', 4420, true);


--
-- Name: asset_fault asset_fault_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset_fault
    ADD CONSTRAINT asset_fault_pkey PRIMARY KEY (asset_failt_id);


--
-- Name: asset asset_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset
    ADD CONSTRAINT asset_pkey PRIMARY KEY (asset_id);


--
-- Name: asset_tag asset_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset_tag
    ADD CONSTRAINT asset_tag_pkey PRIMARY KEY (asset_tag_id);


--
-- Name: department department_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT department_pkey PRIMARY KEY (department_id);


--
-- Name: fault fault_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fault
    ADD CONSTRAINT fault_pkey PRIMARY KEY (fault_id);


--
-- Name: location location_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (location_id);


--
-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (tag_name);


--
-- Name: ticket ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (invoice_id);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);


--
-- Name: asset asset_asset_department_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset
    ADD CONSTRAINT asset_asset_department_fkey FOREIGN KEY (asset_department) REFERENCES public.department(department_id);


--
-- Name: asset_fault asset_fault_asset_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset_fault
    ADD CONSTRAINT asset_fault_asset_id_fkey FOREIGN KEY (asset_id) REFERENCES public.asset(asset_id);


--
-- Name: asset_fault asset_fault_fault_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset_fault
    ADD CONSTRAINT asset_fault_fault_id_fkey FOREIGN KEY (fault_id) REFERENCES public.fault(fault_id);


--
-- Name: asset_tag asset_tag_used_asset_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset_tag
    ADD CONSTRAINT asset_tag_used_asset_fkey FOREIGN KEY (used_asset) REFERENCES public.asset(asset_id);


--
-- Name: asset_tag asset_tag_used_tag_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asset_tag
    ADD CONSTRAINT asset_tag_used_tag_fkey FOREIGN KEY (used_tag) REFERENCES public.tag(tag_name);


--
-- Name: department department_department_location_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT department_department_location_fkey FOREIGN KEY (department_location) REFERENCES public.location(location_id);


--
-- Name: ticket ticket_asset_info_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_asset_info_fkey FOREIGN KEY (asset_info) REFERENCES public.asset(asset_id);


--
-- Name: ticket ticket_user_info_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_user_info_fkey FOREIGN KEY (user_info) REFERENCES public."user"(user_id);


--
-- Name: user user_user_department_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_user_department_fkey FOREIGN KEY (user_department) REFERENCES public.department(department_id);


--
-- PostgreSQL database dump complete
--

