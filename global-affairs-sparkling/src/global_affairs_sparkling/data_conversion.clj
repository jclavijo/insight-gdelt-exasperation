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
