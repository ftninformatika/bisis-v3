<#include "_base_gbns.ftl"
><@signatura/><@odrednica/><@zaglavlje/><@glavniOpis/><@napomene/><@prilozi/><@isbn/><@predmOdred/><@brojUDC/><@inventarni/>
<#assign lm="<BISIS>"+docID?string("#")+"<BR>"+sign+"<BR>"+odred+zag+"<BR>"+opis+nap+pril+isbnBR+"<BR>"+po+brUDC+"<BR>"+inv+"</BISIS>">${lm}
