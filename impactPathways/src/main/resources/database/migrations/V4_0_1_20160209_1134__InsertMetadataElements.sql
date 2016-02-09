INSERT INTO `metadata_elements` 
VALUES      ('1', 
             'DC', 
             'Title', 
             '', 
             'dc.title', 
             'Required', 
             '', 
'Official or unofficial title of the document, data set, image, etc.'), 
            ('2', 
             'DC', 
             'Creator', 
             '', 
             'dc:creator', 
             'Required', 
             '', 
'Creators of the item—typically a person. Could be an organization in case of corporate authors (e.g. Center reports)'
), 
            ('3', 
             'CG', 
             'Creator', 
             'ID', 
             'cg.creator.ID', 
             'Req. when applicable', 
             '', 
'Used if ORCID, SCOPUS, or other type of creator ID scheme is in use. Used in parallel with cg.creator.ID.type'
), 
            ('4', 
             'CG', 
             'Creator', 
             'ID Type', 
             'cg.creator.ID.type', 
             'Req. when applicable', 
             '', 
'Used to indicate the type of Creator ID – ex: SCOPUS, ORCID, etc.'), 
            ('5', 
             'DC', 
             'Subject', 
             '', 
             'dc.subject', 
             'Required', 
             '', 
             'Subject matter of the research, technologies tested, etc.'), 
            ('6', 
             'CG', 
             'Subject', 
             'AGROVOC', 
             'cg.subject.agrovoc', 
             'Optional', 
             'AGROVOC', 
             'AGROVOC subject matter or research area'), 
            ('7', 
             'CG', 
             'Subject', 
             'Domain Specific', 
             'cg.subject.domain-specific', 
             'Optional', 
             'Domain-specific (ex: MeSH)', 
'Subject matter or research area from domain-specific vocabularies, if missing from AGROVOC' 
), 
            ('8', 
             'DC', 
             'Description', 
             '', 
             'dc.description.abstract', 
             'Optional', 
             '', 
             'Abstract or other description of the item'), 
            ('9', 
             'DC', 
             'Publisher', 
             '', 
             'dc.publisher', 
             'Req. when applicable', 
             '', 
             'Entity responsible for publication, distribution, or imprint'), 
            ('10', 
             'DC', 
             'Contributor', 
             '', 
             'dc.contributor', 
             'Required', 
             '', 
'Person, organization, or service making contributions to resource content; CGIAR affiliation'
), 
            ('11', 
             'CG', 
             'Contributor', 
             'Center', 
             'cg.contributor.center', 
             'Required', 
             'CGIAR', 
             'Research Centers and offices with which creator(s) are affiliated' 
), 
            ('12', 
             'CG', 
             'Contributor', 
             'CRP', 
             'cg.contributor.crp', 
             'Required', 
             'CGIAR', 
             'CGIAR Research Program with which the research is affiliated'), 
            ('13', 
             'CG', 
             'Contributor', 
             'Funder', 
             'cg.contributor.funder', 
             'Required', 
             'CGIAR', 
             'Funder, funding agency or sponsor'), 
            ('14', 
             'CG', 
             'Contributor', 
             'Partner', 
             'cg.contributor.partnerId', 
             'Required', 
             'CGIAR', 
             'Partners, funding agencies, other CGIAR centers'), 
            ('15', 
             'CG', 
             'Contributor', 
             'Project', 
             'cg.contributor.project', 
             'Required', 
             'CGIAR', 
             'Name of project with which the research is affiliated'), 
            ('16', 
             'CG', 
             'Contributor', 
             'Project Lead Institution', 
             'cg.contributor.project-lead-institute', 
             'Req. when applicable', 
             'CGIAR', 
'The lead institution for the project (CGIAR or otherwise) connected to the research output being described'
), 
            ('17', 
             'DC', 
             'Date', 
             '', 
             'dc.date', 
             'Required', 
             '', 
             'Publication or creation date'), 
            ('18', 
             'CG', 
             'Date', 
             'Embargo End Date', 
             'cg:date.embargo-end-date', 
             'Req. when applicable', 
             '', 
'Used when an item has an embargo by publisher (ex: 6 or 12-month embargo)'), 
            ('19', 
             'DC', 
             'Type', 
             '', 
             'dc.type', 
             'Required', 
             'CGIAR', 
             'Nature or genre of item/content; e.g., poster, data set'), 
            ('20', 
             'DC', 
             'Format', 
             '', 
             'dc.format', 
             'Required', 
             'CGIAR', 
             'File format of item e.g.: PDF; jpg etc.'), 
            ('21', 
             'DC', 
             'Identifier', 
             '', 
             'dc.identifier', 
             'Required', 
             '', 
             'Unambiguous reference to resource such as doi, uri'), 
            ('22', 
             'DC', 
             'Identifier', 
             'Citation', 
             'dc.identifier.citation', 
             'Optional', 
             '', 
             'Human-readable, standard bibliographic citation for the item'), 
            ('23', 
             'DC', 
             'Source', 
             '', 
             'dc.source', 
             'Req. when applicable', 
             '', 
             'Journal/conference title; vol., no. (year)'), 
            ('24', 
             'DC', 
             'Language', 
             '', 
             'dc.language', 
             'Optional', 
             'ISO 639-1 or ISO 639-2', 
'Language of the item; use ISO 639-1 (alpha-2) or ISO 639-2 (alpha-3).'), 
            ('25', 
             'DC', 
             'Relation', 
             '', 
             'dc.relation', 
             'Optional', 
             '', 
'Supplemental files, e.g. data sets related to publications or larger “whole”' 
), 
            ('26', 
             'DC', 
             'Coverage', 
             '', 
             'dc.coverage', 
             'Req. when applicable', 
             'CGIAR Vocabulary', 
'Geospatial coordinates, countries, regions, sub-regions, chronological period') 
, 
            ('27', 
             'CG', 
             'Coverage', 
             'Region', 
             'cg.coverage.region', 
             'Req. when applicable', 
             'UN Stats', 
'Supra-national areas (above country level) related to the item being described' 
), 
            ('28', 
             'CG', 
             'Coverage', 
             'Country', 
             'cg:coverage.country', 
             'Req. when applicable', 
             'ISO 3166', 
'Country/countries related to the data which was collected in resource'), 
            ('29', 
             'CG', 
             'Coverage', 
             'Admin. Unit', 
             'cg:coverage.admin-unit', 
             'Req. when applicable', 
             'GAUL', 
'Sub-national administrative areas such as provinces, states, or districts'), 
            ('30', 
             'CG', 
             'Coverage', 
             'Geolocation', 
             'cg.coverage.geolocation', 
             'Req. when applicable', 
             '', 
'Coordinates or polygon points for boundaries of area where research was conducted' 
), 
            ('31', 
             'CG', 
             'Coverage', 
             'Start Date', 
             'cg.coverage.start-date', 
             'Req. when applicable', 
             'CGIAR Vocabulary', 
'Chronological period: start date of activity described in resource'), 
            ('32', 
             'CG', 
             'Coverage', 
             'End Date', 
             'cg.coverage.end-date', 
             'Req. when applicable', 
             'CGIAR Vocabulary', 
             'Chronological period: end date of activity described in resource') 
, 
            ('33', 
             'DC', 
             'Rights', 
             '', 
             'dc.rights', 
             'Required', 
             '', 
             'Rights, licensing, or permission statement'), 
            ('34', 
             'CG', 
             'Contact', 
             '', 
             'cg.contact', 
             'Optional', 
             '', 
'For data: email address for group or department to contact in case of questions' 
); 