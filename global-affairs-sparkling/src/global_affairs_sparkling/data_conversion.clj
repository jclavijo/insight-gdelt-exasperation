(ns global-affairs-sparkling.data-conversion
  (:require [clojure.string :as string]))

(def country-codes-hashmap
  {:AF	"Afghanistan"		:AFG	:AF
   :AL	"Albania"		:ALB	:AL
   :DZ	"Algeria"		:DZA	:DZ
   :AS	"American Samoa"		:ASM	:AS
   :AD	"Andorra"		:AND	:AD
   :AO	"Angola"		:AGO	:AO
   :AI	"Anguilla"		:AIA	:AI
   :AQ	"Antarctica"		:ATA	:AQ
   :AG	"Antigua and Barbuda"		:ATG	:AG
   :AR	"Argentina"		:ARG	:AR
   :AM	"Armenia"		:ARM	:AM
   :AW	"Aruba"		:ABW	:AW
   :AU	"Australia"		:AUS	:AU
   :AT	"Austria"		:AUT	:AT
   :AZ	"Azerbaijan"		:AZE	:AZ
   :BS	"Bahamas (the)"		:BHS	:BS
   :BH	"Bahrain"		:BHR	:BH
   :BD	"Bangladesh"		:BGD	:BD
   :BB	"Barbados"		:BRB	:BB
   :BY	"Belarus"		:BLR	:BY
   :BE	"Belgium"		:BEL	:BE
   :BZ	"Belize"		:BLZ	:BZ
   :BJ	"Benin"		:BEN	:BJ
   :BM	"Bermuda"		:BMU	:BM
   :BT	"Bhutan"		:BTN	:BT
   :BO	"Bolivia (Plurinational State of)"		:BOL	:BO
   :BQ	"Bonaire, Sint Eustatius and Saba"		:BES	:BQ
   :BA	"Bosnia and Herzegovina"		:BIH	:BA
   :BW	"Botswana"		:BWA	:BW
   :BV	"Bouvet Island"		:BVT	:BV
   :BR	"Brazil"		:BRA	:BR
   :IO	"British Indian Ocean Territory (the)"		:IOT	:IO
   :BN	"Brunei Darussalam"		:BRN	:BN
   :BG	"Bulgaria"		:BGR	:BG
   :BF	"Burkina Faso"		:BFA	:BF
   :BI	"Burundi"		:BDI	:BI
   :CV	"Cabo Verde"		:CPV	:CV
   :KH	"Cambodia"		:KHM	:KH
   :CM	"Cameroon"		:CMR	:CM
   :CA	"Canada"		:CAN	:CA
   :KY	"Cayman Islands (the)"		:CYM	:KY
   :CF	"Central African Republic (the)"		:CAF	:CF
   :TD	"Chad"		:TCD	:TD
   :CL	"Chile"		:CHL	:CL
   :CN	"China"		:CHN	:CN
   :CX	"Christmas Island"		:CXR	:CX
   :CC	"Cocos (Keeling) Islands (the)"		:CCK	:CC
   :CO	"Colombia"		:COL	:CO
   :KM	"Comoros (the)"		:COM	:KM
   :CD	"Congo (the Democratic Republic of the)"		:COD	:CD
   :CG	"Congo (the)"		:COG	:CG
   :CK	"Cook Islands (the)"		:COK	:CK
   :CR	"Costa Rica"		:CRI	:CR
   :HR	"Croatia"		:HRV	:HR
   :CU	"Cuba"		:CUB	:CU
   :CW	"Curaçao"		:CUW	:CW
   :CY	"Cyprus"		:CYP	:CY
   :CZ	"Czechia"		:CZE	:CZ
   :CI	"Côte d'Ivoire"		:CIV	:CI
   :DK	"Denmark"		:DNK	:DK
   :DJ	"Djibouti"		:DJI	:DJ
   :DM	"Dominica"		:DMA	:DM
   :DO	"Dominican Republic (the)"		:DOM	:DO
   :EC	"Ecuador"		:ECU	:EC
   :EG	"Egypt"		:EGY	:EG
   :SV	"El Salvador"		:SLV	:SV
   :GQ	"Equatorial Guinea"		:GNQ	:GQ
   :ER	"Eritrea"		:ERI	:ER
   :EE	"Estonia"		:EST	:EE
   :SZ	"Eswatini"		:SWZ	:SZ
   :ET	"Ethiopia"		:ETH	:ET
   :FK	"Falkland Islands (the) [Malvinas]"		:FLK	:FK
   :FO	"Faroe Islands (the)"		:FRO	:FO
   :FJ	"Fiji"		:FJI	:FJ
   :FI	"Finland"		:FIN	:FI
   :FR	"France"		:FRA	:FR
   :GF	"French Guiana"		:GUF	:GF
   :PF	"French Polynesia"		:PYF	:PF
   :TF	"French Southern Territories (the)"		:ATF	:TF
   :GA	"Gabon"		:GAB	:GA
   :GM	"Gambia (the)"		:GMB	:GM
   :GE	"Georgia"		:GEO	:GE
   :DE	"Germany"		:DEU	:DE
   :GH	"Ghana"		:GHA	:GH
   :GI	"Gibraltar"		:GIB	:GI
   :GR	"Greece"		:GRC	:GR
   :GL	"Greenland"		:GRL	:GL
   :GD	"Grenada"		:GRD	:GD
   :GP	"Guadeloupe"		:GLP	:GP
   :GU	"Guam"		:GUM	:GU
   :GT	"Guatemala"		:GTM	:GT
   :GG	"Guernsey"		:GGY	:GG
   :GN	"Guinea"		:GIN	:GN
   :GW	"Guinea-Bissau"		:GNB	:GW
   :GY	"Guyana"		:GUY	:GY
   :HT	"Haiti"		:HTI	:HT
   :HM	"Heard Island and McDonald Islands"		:HMD	:HM
   :VA	"Holy See (the)"		:VAT	:VA
   :HN	"Honduras"		:HND	:HN
   :HK	"Hong Kong"		:HKG	:HK
   :HU	"Hungary"		:HUN	:HU
   :IS	"Iceland"		:ISL	:IS
   :IN	"India"		:IND	:IN
   :ID	"Indonesia"		:IDN	:ID
   :IR	"Iran (Islamic Republic of)"		:IRN	:IR
   :IQ	"Iraq"		:IRQ	:IQ
   :IE	"Ireland"		:IRL	:IE
   :IM	"Isle of Man"		:IMN	:IM
   :IL	"Israel"		:ISR	:IL
   :IT	"Italy"		:ITA	:IT
   :JM	"Jamaica"		:JAM	:JM
   :JP	"Japan"		:JPN	:JP
   :JE	"Jersey"		:JEY	:JE
   :JO	"Jordan"		:JOR	:JO
   :KZ	"Kazakhstan"		:KAZ	:KZ
   :KE	"Kenya"		:KEN	:KE
   :KI	"Kiribati"		:KIR	:KI
   :KP	"Korea (the Democratic People's Republic of)"		:PRK	:KP
   :KR	"Korea (the Republic of)"		:KOR	:KR
   :KW	"Kuwait"		:KWT	:KW
   :KG	"Kyrgyzstan"		:KGZ	:KG
   :LA	"Lao People's Democratic Republic (the)"		:LAO	:LA
   :LV	"Latvia"		:LVA	:LV
   :LB	"Lebanon"		:LBN	:LB
   :LS	"Lesotho"		:LSO	:LS
   :LR	"Liberia"		:LBR	:LR
   :LY	"Libya"		:LBY	:LY
   :LI	"Liechtenstein"		:LIE	:LI
   :LT	"Lithuania"		:LTU	:LT
   :LU	"Luxembourg"		:LUX	:LU
   :MO	"Macao"		:MAC	:MO
   :MK	"Macedonia (the former Yugoslav Republic of)"		:MKD	:MK
   :MG	"Madagascar"		:MDG	:MG
   :MW	"Malawi"		:MWI	:MW
   :MY	"Malaysia"		:MYS	:MY
   :MV	"Maldives"		:MDV	:MV
   :ML	"Mali"		:MLI	:ML
   :MT	"Malta"		:MLT	:MT
   :MH	"Marshall Islands (the)"		:MHL	:MH
   :MQ	"Martinique"		:MTQ	:MQ
   :MR	"Mauritania"		:MRT	:MR
   :MU	"Mauritius"		:MUS	:MU
   :YT	"Mayotte"		:MYT	:YT
   :MX	"Mexico"		:MEX	:MX
   :FM	"Micronesia (Federated States of)"		:FSM	:FM
   :MD	"Moldova (the Republic of)"		:MDA	:MD
   :MC	"Monaco"		:MCO	:MC
   :MN	"Mongolia"		:MNG	:MN
   :ME	"Montenegro"		:MNE	:ME
   :MS	"Montserrat"		:MSR	:MS
   :MA	"Morocco"		:MAR	:MA
   :MZ	"Mozambique"		:MOZ	:MZ
   :MM	"Myanmar"		:MMR	:MM
   :NA	"Namibia"		:NAM	:NA
   :NR	"Nauru"		:NRU	:NR
   :NP	"Nepal"		:NPL	:NP
   :NL	"Netherlands (the)"		:NLD	:NL
   :NC	"New Caledonia"		:NCL	:NC
   :NZ	"New Zealand"		:NZL	:NZ
   :NI	"Nicaragua"		:NIC	:NI
   :NE	"Niger (the)"		:NER	:NE
   :NG	"Nigeria"		:NGA	:NG
   :NU	"Niue"		:NIU	:NU
   :NF	"Norfolk Island"		:NFK	:NF
   :MP	"Northern Mariana Islands (the)"		:MNP	:MP
   :NO	"Norway"		:NOR	:NO
   :OM	"Oman"		:OMN	:OM
   :PK	"Pakistan"		:PAK	:PK
   :PW	"Palau"		:PLW	:PW
   :PS	"Palestine, State of"		:PSE	:PS
   :PA	"Panama"		:PAN	:PA
   :PG	"Papua New Guinea"		:PNG	:PG
   :PY	"Paraguay"		:PRY	:PY
   :PE	"Peru"		:PER	:PE
   :PH	"Philippines (the)"		:PHL	:PH
   :PN	"Pitcairn"		:PCN	:PN
   :PL	"Poland"		:POL	:PL
   :PT	"Portugal"		:PRT	:PT
   :PR	"Puerto Rico"		:PRI	:PR
   :QA	"Qatar"		:QAT	:QA
   :RO	"Romania"		:ROU	:RO
   :RU	"Russian Federation (the)"		:RUS	:RU
   :RW	"Rwanda"		:RWA	:RW
   :RE	"Réunion"		:REU	:RE
   :BL	"Saint Barthélemy"		:BLM	:BL
   :SH	"Saint Helena, Ascension and Tristan da Cunha"		:SHN	:SH
   :KN	"Saint Kitts and Nevis"		:KNA	:KN
   :LC	"Saint Lucia"		:LCA	:LC
   :MF	"Saint Martin (French part)"		:MAF	:MF
   :PM	"Saint Pierre and Miquelon"		:SPM	:PM
   :VC	"Saint Vincent and the Grenadines"		:VCT	:VC
   :WS	"Samoa"		:WSM	:WS
   :SM	"San Marino"		:SMR	:SM
   :ST	"Sao Tome and Principe"		:STP	:ST
   :SA	"Saudi Arabia"		:SAU	:SA
   :SN	"Senegal"		:SEN	:SN
   :RS	"Serbia"		:SRB	:RS
   :SC	"Seychelles"		:SYC	:SC
   :SL	"Sierra Leone"		:SLE	:SL
   :SG	"Singapore"		:SGP	:SG
   :SX	"Sint Maarten (Dutch part)"		:SXM	:SX
   :SK	"Slovakia"		:SVK	:SK
   :SI	"Slovenia"		:SVN	:SI
   :SB	"Solomon Islands"		:SLB	:SB
   :SO	"Somalia"		:SOM	:SO
   :ZA	"South Africa"		:ZAF	:ZA
   :GS	"South Georgia and the South Sandwich Islands"		:SGS	:GS
   :SS	"South Sudan"		:SSD	:SS
   :ES	"Spain"		:ESP	:ES
   :LK	"Sri Lanka"		:LKA	:LK
   :SD	"Sudan (the)"		:SDN	:SD
   :SR	"Suriname"		:SUR	:SR
   :SJ	"Svalbard and Jan Mayen"		:SJM	:SJ
   :SE	"Sweden"		:SWE	:SE
   :CH	"Switzerland"		:CHE	:CH
   :SY	"Syrian Arab Republic"		:SYR	:SY
   :TW	"Taiwan (Province of China)"		:TWN	:TW
   :TJ	"Tajikistan"		:TJK	:TJ
   :TZ	"Tanzania, United Republic of"		:TZA	:TZ
   :TH	"Thailand"		:THA	:TH
   :TL	"Timor-Leste"		:TLS	:TL
   :TG	"Togo"		:TGO	:TG
   :TK	"Tokelau"		:TKL	:TK
   :TO	"Tonga"		:TON	:TO
   :TT	"Trinidad and Tobago"		:TTO	:TT
   :TN	"Tunisia"		:TUN	:TN
   :TR	"Turkey"		:TUR	:TR
   :TM	"Turkmenistan"		:TKM	:TM
   :TC	"Turks and Caicos Islands (the)"		:TCA	:TC
   :TV	"Tuvalu"		:TUV	:TV
   :UG	"Uganda"		:UGA	:UG
   :UA	"Ukraine"		:UKR	:UA
   :AE	"United Arab Emirates (the)"		:ARE	:AE
   :GB	"United Kingdom of Great Britain and Northern Ireland (the)"		:GBR	:GB
   :UM	"United States Minor Outlying Islands (the)"		:UMI	:UM
   :US	"United States of America (the)"		:USA	:US
   :UY	"Uruguay"		:URY	:UY
   :UZ	"Uzbekistan"		:UZB	:UZ
   :VU	"Vanuatu"		:VUT	:VU
   :VE	"Venezuela (Bolivarian Republic of)"		:VEN	:VE
   :VN	"Viet Nam"		:VNM	:VN
   :VG	"Virgin Islands (British)"		:VGB	:VG
   :VI	"Virgin Islands (U.S.)"		:VIR	:VI
   :WF	"Wallis and Futuna"		:WLF	:WF
   :EH	"Western Sahara"		:ESH	:EH
   :YE	"Yemen"		:YEM	:YE
   :ZM	"Zambia"		:ZMB	:ZM
   :ZW	"Zimbabwe"		:ZWE	:ZW
   :AX	"Aland Islands"		:ALA	:AX})

(def gdelt-columns-hashmap
  {"GLOBALEVENTID" 0
   "SQLDATE" 1
   "MonthYear" 2
   "Year" 3
   "FractionDate" 4
   "Actor1Code" 5
   "Actor1Name" 6
   "Actor1CountryCode" 7
   "Actor1KnownGroupCode" 8
   "Actor1EthnicCode" 9
   "Actor1Religion1Code" 10
   "Actor1Religion2Code" 11
   "Actor1Type1Code" 12
   "Actor1Type2Code" 13
   "Actor1Type3Code" 14
   "Actor2Code" 15
   "Actor2Name" 16
   "Actor2CountryCode" 17
   "Actor2KnownGroupCode" 18
   "Actor2EthnicCode" 19
   "Actor2Religion1Code" 20
   "Actor2Religion2Code" 21
   "Actor2Type1Code" 22
   "Actor2Type2Code" 23
   "Actor2Type3Code" 24
   "IsRootEvent" 25
   "EventCode" 26
   "EventBaseCode" 27
   "EventRootCode" 28
   "QuadClass" 29
   "GoldsteinScale" 30
   "NumMentions" 31
   "NumSources" 32
   "NumArticles" 33
   "AvgTone" 34
   "Actor1Geo_Type" 35
   "Actor1Geo_FullName" 36
   "Actor1Geo_CountryCode" 37
   "Actor1Geo_ADM1Code" 38
   "Actor1Geo_ADM2Code" 39
   "Actor1Geo_Lat" 40
   "Actor1Geo_Long" 41
   "Actor1Geo_FeatureID" 42
   "Actor2Geo_Type" 43
   "Actor2Geo_FullName" 44
   "Actor2Geo_CountryCode" 45
   "Actor2Geo_ADM1Code" 46
   "Actor2Geo_ADM2Code" 47
   "Actor2Geo_Lat" 48
   "Actor2Geo_Long" 49
   "Actor2Geo_FeatureID" 50
   "ActionGeo_Type" 51
   "ActionGeo_FullName" 52
   "ActionGeo_CountryCode" 53
   "ActionGeo_ADM1Code" 54
   "ActionGeo_ADM2Code" 55
   "ActionGeo_Lat" 56
   "ActionGeo_Long" 57
   "ActionGeo_FeatureID" 58
   "DATEADDED" 59
   "SOURCEURL" 60})

(def event-codes
  {
   :010	"Make statement"
   :011	"Decline comment"
   :012	"Make pessimistic comment"
   :013	"Make optimistic comment"
   :014	"Consider policy option"
   :015	"Acknowledge or claim responsibility"
   :016	"Deny responsibility"
   :017	"Engage in symbolic act"
   :018	"Make empathetic comment"
   :019	"Express accord"
   :02	"APPEAL"
   :020	"Make an appeal or request"
   :021	"Appeal for material cooperation"
   :0211	"Appeal for economic cooperation"
   :0212	"Appeal for military cooperation"
   :0213	"Appeal for judicial cooperation"
   :0214	"Appeal for intelligence cooperation"
   :022	"Appeal for diplomatic cooperation (such as policy support)"
   :023	"Appeal for aid"
   :0231	"Appeal for economic aid"
   :0232	"Appeal for military aid"
   :0233	"Appeal for humanitarian aid"
   :0234	"Appeal for military protection or peacekeeping"
   :024	"Appeal for political reform"
   :0241	"Appeal for change in leadership"
   :0242	"Appeal for policy change"
   :0243	"Appeal for rights"
   :0244	"Appeal for change in institutions, regime"
   :025	"Appeal for target to yield"
   :0251	"Appeal for easing of administrative sanctions"
   :0252	"Appeal for easing of political dissent"
   :0253	"Appeal for release of persons or property"
   :0254	"Appeal for easing of economic sanctions, boycott, or embargo"
   :0255	"Appeal for target to allow international involvement (non-mediation)"
   :0256	"Appeal for de-escalation of military engagement"
   :026	"Appeal to others to meet or negotiate"
   :027	"Appeal to others to settle dispute"
   :028	"Appeal to engage in or accept mediation"
   :030	"Express intent to cooperate"
   :031	"Express intent to engage in material cooperation"
   :0311	"Express intent to cooperate economically"
   :0312	"Express intent to cooperate militarily"
   :0313	"Express intent to cooperate on judicial matters"
   :0314	"Express intent to cooperate on intelligence"
   :032	"Express intent to engage in diplomatic cooperation (such as policy support)"
   :033	"Express intent to provide material aid"
   :0331	"Express intent to provide economic aid"
   :0332	"Express intent to provide military aid"
   :0333	"Express intent to provide humanitarian aid"
   :0334	"Express intent to provide military protection or peacekeeping"
   :034	"Express intent to institute political reform"
   :0341	"Express intent to change leadership"
   :0342	"Express intent to change policy"
   :0343	"Express intent to provide rights"
   :0344	"Express intent to change institutions, regime"
   :035	"Express intent to yield"
   :0351	"Express intent to ease administrative sanctions"
   :0352	"Express intent to ease popular dissent"
   :0353	"Express intent to release persons or property"
   :0354	"Express intent to ease economic sanctions, boycott, or embargo"
   :0355	"Express intent to allow international involvement (non-mediation)"
   :0356	"Express intent to de-escalate military engagement"
   :036	"Express intent to meet or negotiate"
   :037	"Express intent to settle dispute"
   :038	"Express intent to accept mediation"
   :039	"Express intent to mediate"
   :04	"CONSULT"
   :040	"Consult"
   :041	"Discuss by telephone"
   :042	"Make a visit"
   :043	"Host a visit"
   :044	"Meet at a 'third' location"
   :045	"Engage in mediation"
   :046	"Engage in negotiation"
   :05	"ENGAGE IN DIPLOMATIC COOPERATION"
   :050	"Engage in diplomatic cooperation"
   :051	"Praise or endorse"
   :052	"Defend verbally"
   :053	"Rally support on behalf of"
   :054	"Grant diplomatic recognition"
   :055	"Apolgize"
   :056	"Forgive"
   :057	"Sign formal agreement"
   :06	"ENGAGE IN MATERIAL COOPERATION"
   :060	"Engage in material cooperation"
   :061	"Cooperate economically"
   :062	"Cooperate militarily"
   :063	"Engage in judicial cooperation"
   :064	"Share intelligence or information"
   :07	"PROVIDE AID"
   :070	"Provide aid"
   :071	"Provide economic aid"
   :072	"Provide military aid"
   :073	"Provide humanitarian aid"
   :074	"Provide military protection or peacekeeping"
   :075	"Grant asylum"
   :08	"YIELD"
   :080	"Yield"
   :081	"Ease administrative sanctions"
   :0811	"Ease restrictions on political freedoms"
   :0812	"Ease ban on political parties or politicians"
   :0813	"Ease curfew"
   :0814	"Ease state of emergency or martial law"
   :082	"Ease political dissent"
   :083	"Accede to requests or demands for political reform"
   :0831	"Accede to demands for change in leadership"
   :0832	"Accede to demands for change in policy"
   :0833	"Accede to demands for rights"
   :0834	"Accede to demands for change in institutions, regime"
   :084	"Return, release"
   :0841	"Return, release person(s)"
   :0842	"Return, release property"
   :085	"Ease economic sanctions, boycott, embargo"
   :086	"Allow international involvement"
   :0861	"Receive deployment of peacekeepers"
   :0862	"Receive inspectors"
   :0863	"Allow delivery of humanitarian aid"
   :087	"De-escalate military engagement"
   :0871	"Declare truce, ceasefire"
   :0872	"Ease military blockade"
   :0873	"Demobilize armed forces"
   :0874	"Retreat or surrender militarily"
   :09	"INVESTIGATE"
   :090	"Investigate"
   :091	"Investigate crime, corruption"
   :092	"Investigate human rights abuses"
   :093	"Investigate military action"
   :094	"Investigate war crimes"
   :10	"DEMAND"
   :100	"Demand"
   :101	"Demand material cooperation"
   :1011	"Demand economic cooperation"
   :1012	"Demand military cooperation"
   :1013	"Demand judicial cooperation"
   :1014	"Demand intelligence cooperation"
   :102	"Demand diplomatic cooperation (such as policy support)"
   :103	"Demand material aid"
   :1031	"Demand economic aid"
   :1032	"Demand military aid"
   :1033	"Demand humanitarian aid"
   :1034	"Demand military protection or peacekeeping"
   :104	"Demand political reform"
   :1041	"Demand change in leadership"
   :1042	"Demand policy change"
   :1043	"Demand rights"
   :1044	"Demand change in institutions, regime"
   :105	"Demand that target yields"
   :1051	"Demand easing of adminstrative sanctions"
   :1052	"Demand easing of political dissent"
   :1053	"Demand release of persons or property"
   :1054	"Demand easing of economic sanctions, boycott, or embargo"
   :1055	"Demand that target allows international involvement (non-mediation)"
   :1056	"Demand de-escalation of military engagement"
   :106	"Demand meeting, negotiation"
   :107	"Demand settling of dispute"
   :108	"Demand mediation"
   :11	"DISAPPROVE"
   :110	"Disapprove"
   :111	"Criticize or denounce"
   :112	"Accuse"
   :1121	"Accuse of crime, corruption"
   :1122	"Accuse of human rights abuses"
   :1123	"Accuse of aggression"
   :1124	"Accuse of war crimes"
   :1125	"Accuse of espionage, treason"
   :113	"Rally opposition against"
   :114	"Complain officially"
   :115	"Bring lawsuit against"
   :116	"Find guilty or liable (legally)"
   :12	"REJECT"
   :120	"Reject"
   :121	"Reject material cooperation"
   :1211	"Reject economic cooperation"
   :1212	"Reject military cooperation"
   :1213	"Reject judicial cooperation"
   :1214	"Reject intelligence cooperation"
   :122	"Reduce or stop material aid"
   :1231	"Reject request for change in leadership"
   :1232	"Reject request for policy change"
   :1233	"Reject request for rights"
   :1234	"Reject request for change in institutions, regime"
   :1221	"Reject request for economic aid"
   :1222	"Reject request for military aid"
   :1223	"Reject request for humanitarian aid"
   :1224	"Reject request for military protection or peacekeeping"
   :123	"Reject request or demand for political reform"
   :124	"Decline to yield"
   :1241	"Decline to ease administrative sanctions"
   :1242	"Decline to ease popular dissent"
   :1243	"Decline to release persons or property"
   :1244	"Decline to ease economic sanctions, boycott, or embargo"
   :1245	"Decline to allow international involvement (non-mediation)"
   :1246	"Decline to de-escalate military engagement"
   :125	"Reject proposal to meet, discuss, or negotiate"
   :126	"Reject mediation"
   :127	"Reject plan, agreement to settle dispute"
   :128	"Defy norms, law"
   :129	"Veto"
   :130	"Threaten"
   :131	"Threaten non-force"
   :1311	"Threaten to reduce or stop aid"
   :1312	"Threaten to boycott, embargo, or sanction"
   :1313	"Threaten to reduce or break relations"
   :132	"Threaten with administrative sanctions"
   :1321	"Threaten to impose restrictions on freedoms of speech and expression"
   :1322	"Threaten to ban political parties or politicians"
   :1323	"Threaten to impose curfew"
   :1324	"Threaten to impose state of emergency or martial law"
   :133	"Threaten with political dissent, protest"
   :134	"Threaten to halt negotiations"
   :135	"Threaten to halt mediation"
   :136	"Threaten to halt (expel or withdraw) international involvement (non-mediation)"
   :137	"Threaten with violent repression"
   :13	"Threaten with repression"
   :138	"Threaten to use military force"
   :1381	"Threaten blockade"
   :1382	"Threaten occupation"
   :1383	"Threaten unconventional violence"
   :1384	"Threaten conventional attack"
   :1385	"Threaten attack with WMD"
   :139	"Give ultimatum"
   :14	"PROTEST"
   :140	"Engage in political dissent"
   :141	"Demonstrate or rally"
   :1411	"Demonstrate for leadership change"
   :1412	"Demonstrate for policy change"
   :1413	"Demonstrate for rights"
   :1414	"Demonstrate for change in institutions, regime"
   :142	"Conduct hunger strike"
   :1421	"Conduct hunger strike for leadership change"
   :1422	"Conduct hunger strike for policy change"
   :1423	"Conduct hunger strike for rights"
   :1424	"Conduct hunger strike for change in institutions, regime"
   :143	"Conduct strike or boycott"
   :1431	"Conduct strike or boycott for change in leadership"
   :1432	"Conduct strike or boycott for policy change"
   :1433	"Conduct strike or boycott for rights"
   :1434	"Conduct strike or boycott for change in institutions, regime"
   :144	"Obstruct passage, block"
   :1441	"Obstruct passage to demand change in leadership"
   :1442	"Obstruct passage to demand policy change"
   :1443	"Obstruct passage to demand rights"
   :1444	"Ostruct passage to demand change in institutions, regime"
   :145	"Protest violently, riot"
   :1451	"Engage in violent protest for change in leadership"
   :1452	"Engage in violent protest for policy change"
   :1453	"Engage in violent protest for rights"
   :1454	"Engage in violent protest for change in institutions, regime"
   :15	"EXHIBIT MILITARY POSTURE"
   :150	"Demonstrate military or police power"
   :151	"Increase police alert status"
   :152	"Increase military alert status"
   :153	"Mobilize or increase police power"
   :154	"Mobilize or increase armed forces"
   :16	"REDUCE RELATIONS"
   :160	"Reduce relations"
   :161	"Reduce or break diplomatic relations"
   :162	"Reduce or stop aid"
   :1621	"Reduce or stop economic assistance"
   :1622	"Reduce or stop military assistance"
   :1623	"Reduce or stop humanitarian assistance"
   :163	"Impose embargo, boycott, or sanctions"
   :164	"Halt negotiations"
   :165	"Halt mediation"
   :166	"Expel or withdraw"
   :1661	"Expel or withdraw peacekeepers"
   :1662	"Expel or withdraw inspectors, observers"
   :1663	"Expel or withdraw aid agencies"
   :17	"COERCE"
   :170	"Coerce"
   :171	"Seize or damage property"
   :1711	"Confiscate property"
   :1712	"Destroy property"
   :172	"Impose administrative sanctions"
   :1721	"Impose restrictions on freedoms of speech and expression"
   :1722	"Ban political parties or politicians"
   :1723	"Impose curfew"
   :1724	"Impose state of emergency or martial law"
   :173	"Arrest, detain, or charge with legal action"
   :174	"Expel or deport individuals"
   :175	"Use tactics of violent repression"
   :18	"ASSAULT"
   :180	"Use unconventional violence"
   :181	"Abduct, hijack, or take hostage"
   :182	"Physically assault"
   :1821	"Sexually assault"
   :1822	"Torture"
   :1823	"Kill by physical assault"
   :183	"Conduct suicide, car, or other non-military bombing"
   :1831	"Carry out suicide bombing"
   :1832	"Carry out car bombing"
   :1833	"Carry out roadside bombing"
   :184	"Use as human shield"
   :185	"Attempt to assassinate"
   :186	"Assassinate"
   :19	"FIGHT"
   :190	"Use conventional military force"
   :191	"Impose blockade, restrict movement"
   :192	"Occupy territory"
   :193	"Fight with small arms and light weapons"
   :194	"Fight with artillery and tanks"
   :195	"Employ aerial weapons"
   :196	"Violate ceasefire"
   :20	"ATTACK WITH WEAPONS OF MASS DESTRUCTION"
   :200	"Use unconventional mass violence"
   :201	"Engage in mass expulsion"
   :202	"Engage in mass killings"
   :203	"Engage in ethnic cleansing"
   :204	"Use weapons of mass destruction"
   :2041	"Use chemical, biological, or radiological weapons"
   :2042	"Detonate nuclear weapons"
   })
