-- Function: public.setval(character varying, character varying, character varying, character varying, bigint)

-- DROP FUNCTION public.setval(character varying, character varying, character varying, character varying, bigint);

CREATE OR REPLACE FUNCTION public.setval(
    psystem character varying,
    psubsys character varying,
    pmodule character varying,
    ptable character varying,
    pidentity bigint)
  RETURNS bigint AS
$BODY$
   declare real_current_year bigint;
   begin
	   real_current_year:=extract(year from now())*1000;
/* sql server 要加上显式事务 开始 */
     UPDATE g_identity
     SET identity_value=pidentity, current_year=real_current_year, update_time=now() 
     WHERE system_name=psystem and sub_sys=psubsys and module_name=pmodule and table_full_name=ptable;
     RETURN currval(psystem, psubsys, pmodule, ptable);
/* sql server 要加上显式事务 结束 */
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.setval(character varying, character varying, character varying, character varying, bigint)
  OWNER TO postgres;
