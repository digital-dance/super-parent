-- Function: public.currval(character varying, character varying, character varying, character varying)

-- DROP FUNCTION public.currval(character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION public.currval(
    psystem character varying,
    psubsys character varying,
    pmodule character varying,
    ptable character varying)
  RETURNS bigint AS
$BODY$
  /* sql server 要加上显式事务 开始 */
    DECLARE g_VALUE bigint;
    DECLARE pVALUE bigint;
    declare old_current_year bigint;
    declare real_current_year bigint;
    BEGIN
	    g_VALUE:=0;
	    pVALUE:=-1;
			old_current_year:=-1;
	    real_current_year:=extract(year from now())*1000;
	    
	    SELECT identity_value, current_year INTO pVALUE, old_current_year 
	    FROM g_identity
	    WHERE system_name=psystem and sub_sys=psubsys and module_name=pmodule and table_full_name=ptable;
	    
	    IF ( ( pVALUE = -1 ) or (pVALUE is NULL) ) THEN
		  INSERT INTO g_identity(system_name, sub_sys, module_name, table_full_name, identity_value, increment_value, current_year, create_time) VALUES(psystem , psubsys, pmodule, ptable, real_current_year, 1, real_current_year, now());
		  g_VALUE:=real_current_year;
	    ELSEIF( ( old_current_year <> real_current_year ) and ( old_current_year <> -1 ) and ( old_current_year NOTNULL ) ) THEN
		  g_VALUE:= real_current_year;
		  UPDATE g_identity 
		     SET identity_value=real_current_year, current_year=real_current_year, update_time=now()  
		     WHERE system_name=psystem and sub_sys=psubsys and module_name=pmodule and table_full_name=ptable;
	    ELSE
			g_VALUE:= pVALUE;
	    END IF;
	   
	    RETURN g_VALUE;
	    /* sql server 要加上显式事务 结束 */
	 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.currval(character varying, character varying, character varying, character varying)
  OWNER TO postgres;
