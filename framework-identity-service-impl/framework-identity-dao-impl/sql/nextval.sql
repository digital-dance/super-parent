-- Function: public.nextval(character varying, character varying, character varying, character varying)

-- DROP FUNCTION public.nextval(character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION public.nextval(
    psystem character varying,
    psubsys character varying,
    pmodule character varying,
    ptable character varying)
  RETURNS bigint AS
$BODY$
  /* sql server 要加上显式事务 开始 */   
  BEGIN
     /* sql server 要加上显式事务 开始 */
     UPDATE g_identity
     SET identity_value = identity_value + increment_value, update_time=now() 
     WHERE system_name=psystem and sub_sys=psubsys and module_name=pmodule and table_full_name=ptable;
     RETURN currval(psystem, psubsys, pmodule, ptable);
     /* sql server 要加上显式事务 结束 */
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.nextval(character varying, character varying, character varying, character varying)
  OWNER TO postgres;
