PL/SQL Developer Test script 3.0
16
-- Created on 8/7/2008 by WWANG 
declare 
  -- Local variables here
  pw varchar2(80);
  decrypted_pw varchar2(80);
  
begin
  -- retrieve encrypted pw
  select login_pw into pw from account_logins al where al.login_id='OLUSER999';
  
  
  dbms_obfuscation_toolkit.DESDecrypt(input_string => pw,
                                    key_string => RPAD('1254224', 8, ' '),
                                    decrypted_string => decrypted_pw);
  DBMS_OUTPUT.put_line('decrypted_pw: ' || decrypted_pw);
end;
0
0
