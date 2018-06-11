<#include "_koncept_piv.ftl"
><#assign udcList=false
><#assign arap=false
><#assign rim=false
><#assign predm=false
><#assign udc=false















><#assign udc=false


><#macro base
><#assign brUDC=""
><#assign out=""
><#assign outB=""

><@odrednica/><#--
--><#assign out=out+odred

><@zaglavlje/><#--
--><#assign out=out+zag
><@alone532/><#--
--><#assign out=out+a532
><@glavniOpis/><#--

--><#assign out=out+opis

><@napomene/><@prilozi/><@isbn/><@predmOdred1/><#--
--><#assign out=out+nap+pril+isbnBR


><#if !udcList

      ><@brojUDC/><#--
      --><#assign out=out+brUDC
></#if
><#if po!=""
    ><#assign out=out+"<BR>"+po
></#if    

><#if out=""
      ><#assign out=out+"ID="
><#else
      ><@brojID/><#--
      --><#if brID
              ><#assign out=out+"<BR>ID="
      ><#else
              ><#assign out=out+"<BR><BR>ID="
      ></#if
      
      
      ><#assign out=out+docID?string("#")+"<BR>"
></#if

></#macro  

><#macro baseSednice
><#assign brUDC=""
><#assign out=""
><#assign outB=""


><@glavniOpis1/><#--

--><#assign out=out+opis

><@napomene/><@prilozi/><@isbn/><@predmOdred1/><#--
--><#assign out=out+nap+pril+isbnBR



      ><@brojUDC/><#--
      --><#assign out=out+brUDC

><#if po!=""
    ><#assign out=out+"<BR>"+po
></#if    

><#if out=""
      ><#assign out=out+"ID="
><#else
      ><@brojID/><#--
      --><#if brID
              ><#assign out=out+"<BR>ID="
      ><#else
              ><#assign out=out+"<BR><BR>ID="
      ></#if
      
      
      ><#assign out=out+docID?string("#")+"<BR>"
></#if

></#macro  

><#macro getSporedniListic  
><#assign sign=""
><#assign sign1=""
><#assign sListic=""
><#assign inv=""
><#assign out=""
><@odrednica/><@zaglavlje/><@alone532/><#--
--><@base/><#--
--><#assign firstSpored=true
   ><#if f996?exists
      ><#assign brojac=0
      ><#list f996 as field             
                   ><#assign val=""
                   ><#assign allSF="d"
                   ><@field996FF field/><#-- 
                   --><#if val!="" && sign1!=val
                             ><#assign sign1=val 
                             ><#assign word=val
                             ><#if brojac=brSignatura
                                  ><#assign sign="<sig>"+sign+"</sig>"
                                  ><#if zag="" && odred="" && a532="" 
                                        ><#assign sOdrednica=sOdrednica+"<BR>"
                                  ></#if
                                  ><#if firstSpored
                                       ><#assign firstSpored=false
                                       ><#assign sListic=sListic+sign+"<BR>"+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out+inv 
                                  ><#else
                                       ><#assign sListic=sListic+"<np>"+sign+"<BR>"+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out+inv
                                  ></#if
                                  ><#assign sign=""
                                  ><#assign inv=""
                                  ><#assign brojac=0
                             ></#if
                             ><#assign val=word
                             ><@rightAlign/><#--
                             --><#assign sign=sign+outB+val+"<BR>"
                             ><#assign brojac=brojac+1
                   ></#if
                   ><#assign val=""
                   ><#assign allSF="f"
                   ><@field996FF field/><#--
                         
                   --><#if val!="" 
                              ><#assign word=val
                              ><@rightAlign/><#--
                              --><#assign inv=inv+outB+val+"<BR>"
                   ></#if   
       ></#list
       ><#if brojac!=0
                  ><#assign sign="<sig>"+sign+"</sig>"
                  ><#if zag="" && odred="" && a532="" 
                      ><#assign sOdrednica=sOdrednica+"<BR>"
                  ></#if
                  ><#if firstSpored
                      ><#assign first=false
                      ><#assign sListic=sListic+sign+"<BR>"+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out+inv 
                  ><#else
                      ><#assign sListic=sListic+"<np>"+sign+"<BR>"+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out+inv
                  ></#if
       
       ></#if
><#else
    ><#assign sListic=sListic+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out      
></#if


></#macro






><#macro getMonografski
><#assign lm=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=""
><#assign firstLM=true
><#assign existsSign=false
><#assign old=","
><@base/><#--
--><#if f996?exists
      ><#assign brojac=0
      ><#list f996 as field             
             ><#assign val=""
             ><#assign allSF="d"
             ><@field996FF field/><#-- 
             --><#if val!="" && old?index_of(","+val+",")=-1
                   ><#assign old=old+val+","  
                   ><#assign existsSign=true                        
                   ><#assign word=val 
                   ><#assign sign1=val
                   ><#if brojac=brSignatura
                       ><#if zag="" && odred="" && a532="" 
                              ><#assign sign=sign+"<BR>"
                       ></#if
                       ><#assign sign="<sig>"+sign+"</sig>"
                       ><#if firstLM
                             ><#assign firstLM=false
                             ><#assign lm=lm+sign+out+inv 
                       ><#else
                             ><#assign lm=lm+"<np><BR>"+sign+out+inv      
                       ></#if
                       ><#assign sign=""
                       ><#assign inv=""
                       ><#assign brojac=0
                   ></#if
                   ><#assign val=word    
                   ><@rightAlign/><#--
                   --><#assign sign=sign+outB+val+"<BR>"
                   ><#assign brojac=brojac+1 
                   ><@inventar/><#--
             --></#if
             
                  
       ></#list      
       ><#if brojac!=0
                  ><#if zag="" && odred="" && a532="" 
                      ><#assign sign=sign+"<BR>"
                  ></#if
                  ><#assign sign="<sig>"+sign+"</sig>"
                  ><#if firstLM
                      ><#assign first=false
                      ><#assign lm=lm+sign+out+inv 
                  ><#else
                      ><#assign lm=lm+"<np><BR>"+sign+out+inv      
                  ></#if       
       ></#if

           
></#if
><#if !existsSign
  ><#assign lm=lm+out 
></#if  
></#macro


><#macro getSednice
><#assign lm=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=""
><#assign firstLM=true
><#assign existsSign=false
><#assign potR=""
><#assign old=","
><@baseSednice/><#--
--><#if f996?exists
      ><#assign brojac=0
      ><#list f996 as field             
             ><#assign val=""
             ><#assign allSF="d"
             ><@field996FF field/><#-- 
             --><#if val!="" && old?index_of(","+val+",")=-1
                  ><#assign old=old+val+","                             
                   ><#assign existsSign=true                        
                   ><#assign word=val 
                   ><#assign sign1=val
                   ><#if brojac=brSignatura
                       
                       ><#assign sign="<sig>"+sign+"</sig>"
                       ><#if potR!=""
                         ><#assign potR=potR+"<BR>"
                       ></#if  
                       ><#if firstLM
                             ><#assign firstLM=false
                             ><#assign lm=lm+sign+out+potR+inv 
                       ><#else
                             ><#assign lm=lm+"<np><BR>"+sign+out+potR+inv      
                       ></#if
                       ><#assign sign=""
                       ><#assign inv=""
                       ><#assign brojac=0
                       ><#assign potR=""
                   ></#if
                   ><#assign val=word    
                   ><@rightAlign/><#--
                   --><#assign sign=sign+outB+val+"<BR>"
                   ><#assign brojac=brojac+1 
                   ><@inventar/><@polje996potpoljeR/><#--
             --></#if
                        
                  
       ></#list      
       ><#if brojac!=0
                  
                  ><#assign sign="<sig>"+sign+"</sig>"
                  ><#if firstLM
                      ><#assign first=false
                      ><#assign lm=lm+sign+out+potR+inv 
                  ><#else
                      ><#assign lm=lm+"<np><BR>"+sign+out+potR+inv      
                  ></#if       
       ></#if

           
></#if
><#if !existsSign
  ><#assign lm=lm+out 
></#if  
></#macro






><#macro getUDCListic
><#assign UDClis=""
><#assign prvi=true

><#if f675?exists
          ><#list f675 as field
                  ><#assign udcList=true
                  ><#assign udc=false
                  ><#assign val=""
                  ><#assign allSF="a"
                  ><@field010 field/><#--
                  --><#if val!=""
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if UDClis!=""
                                       ><#assign UDClis=UDClis+"<np>"+sListic
                               ><#else
                                       ><#assign UDClis=UDClis+sListic 
                               ></#if    
                 ></#if                    
           ></#list
></#if
><#assign udc=false 
><#assign udcList=false
></#macro    


             
><#macro getPredmListic
><#assign predmLis=""
><#assign prvi=true

><#if f600?exists
      
      ><#list f600 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                
                ><#assign udcList=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field600 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if  
                ></#if        
           ></#if 
      ></#list
></#if
                               
                              



><#if f601?exists
      
      ><#list f601 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field601 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if





><#if f602?exists
      
      ><#list f602 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field602 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if


><#if f605?exists
      
      ><#list f605 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign udc=false
          
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field605 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if



><#if f606?exists
      
      ><#list f606 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field606 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign n=val><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if



><#if f607?exists
      
      ><#list f607 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field606 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if



><#if f608?exists
      
      ><#list f608 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field606 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if



><#if f609?exists
      
      ><#list f609 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field606 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if 
      ></#list
></#if


    



></#macro





><#macro getAutorski
><#assign autorLis=""
><#assign prvi=true

><#if f700?exists
      
      ><#list f700 as field
           ><#if field.ind1="1"  
                
          
                ><#assign val=""
                ><#assign allSF="abcdf" 
                ><@field700 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if




><#if f701?exists
      
      ><#list f701 as field
           ><#if field.ind1="1"  
                
          
                ><#assign val=""
                ><#assign allSF="abcdf" 
                ><@field700 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if





><#if f702?exists
      
      ><#list f702 as field
           ><#if field.ind1="1" 
                
          
                ><#assign val=""
                ><#assign allSF="abcdf" 
                ><@field700 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if


><#if f711?exists
      
      ><#list f711 as field
                ><#assign val=""
                ><#assign allSF="abcdefgh" 
                ><@field710 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if
              
      ></#list
></#if



><#if f712?exists
      
      ><#list f712 as field
                ><#assign val=""
                ><#assign allSF="abcdefgh" 
                ><@field710 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if
      ></#list
></#if



><#if f721?exists
      
      ><#list f721 as field
                ><#assign val=""
                ><#assign allSF="af" 
                ><@field720 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if
      ></#list
></#if



><#if f722?exists     
      ><#list f722 as field                     
                ><#assign val=""
                ><#assign allSF="af" 
                ><@field720 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if
      ></#list
></#if



><#if f423?exists
      
      ><#list f423 as secField
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field423Analitika secField /><#--
              
                --><#if val!=""
                         ><#assign word=val
                         ><#list word?split("<split423>") as val                                 
                                 
                                 ><#assign number=1
                                 ><@upperFirstN/><#--
                                 --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                         ></#list
                 ></#if 
      ></#list
></#if

></#macro


><#macro getSerijski
><#assign ls=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=""
><#assign firstLS=true
><#assign existsSign=false
><#assign word=""
><#assign old=","
><@odrednica/><@zaglavljeSer/><@glavniOpis/><@napomeneSerijska/><@issn/><@specGodista/><@brojUDCSerijska/><#--
--><#if f997?exists
      ><#assign existsSign=true
      ><#assign brojac=0
      ><#list f997 as field             
                   ><#assign val=""
                   ><#assign allSF="d"
                   ><@field996FF field/><#-- 
                   --><#if val!="" && old?index_of(","+val+",")=-1
                             ><#assign old=old+val+","
                             ><#assign word=val                             
                             ><#if brojac=brSignatura
                                 ><#assign sign="<sig>"+sign+"</sig><BR>"
                                  
                                 ><#if firstLS
                                      ><#assign firstLS=false
                                      ><#assign ls=ls+zagS+sign+odred+opis+napS+issnBR
                                      ><#if issnBR!=""
                                           ><#assign ls=ls+"<BR>"
                                      ></#if                                        
                                      ><#assign ls=ls+brUDC 
                                      
                                      
                                      ><#if !ls?ends_with("<BR>")
                                             ><#assign ls=ls+"<BR>"
                                       ></#if  
                                       ><#assign ls=ls+"<BR>"+inv  
                                  ><#else
                                       ><#assign ls=ls+"<np><BR>"+zagS+sign+odred+opis+napS+issnBR
                                       ><#if issnBR!=""
                                           ><#assign ls=ls+"<BR>"
                                       ></#if      
                                       ><#assign ls=ls+"<BR>"+brUDC  

                                       ><#if !ls?ends_with("<BR>")
                                             ><#assign ls=ls+"<BR>"
                                       ></#if  
                                       ><#assign ls=ls+"<BR>"+inv     
                                  ></#if
                                  ><#assign sign=""
                                  ><#assign inv=""
                                  ><#assign brojac=0
                             ></#if
                             ><#assign val=word
                             ><@rightAlign/><#--
                             --><#assign sign=sign+outB+val+"<BR>"
                             ><#assign brojac=brojac+1 
                             ><@inventarSerijska/><#-- 
                   --></#if
                   
                   
                   
                   
       ></#list      
       ><#if brojac!=0
                  ><#assign sign="<sig>"+sign+"</sig><BR>"
                  ><#if firstLS
                      ><#assign first=false                      
                      ><#assign ls=ls+zagS+sign+odred+opis+napS+issnBR
                      ><#if issnBR!=""
                          ><#assign ls=ls+"<BR>"
                      ></#if    
                      ><#assign ls=ls+"<BR>"+brUDC  
                      
                      
                      ><#if !ls?ends_with("<BR>")
                          ><#assign ls=ls+"<BR>"
                      ></#if
                      ><#assign ls=ls+"<BR>"+inv  
                  ><#else                      
                      ><#assign ls=ls+"<np><BR>"+zagS+sign+odred+opis+napS+issnBR 
                      ><#if issnBR!=""
                            ><#assign ls=ls+"<BR>"
                      ></#if    
                      ><#assign ls=ls+"<BR>"+brUDC 
                      
                      ><#if !ls?ends_with("<BR>")
                           ><#assign ls=ls+"<BR>"
                      ></#if
                      ><#assign ls=ls+"<BR>"+inv    
                  ></#if
       ></#if
></#if
><#if !existsSign
    ><#assign ls="<sig><B>"+nemaSignature+"</B><BR></sig><BR>"+zagS+odred+opis+napS+issnBR+"<BR>"           
></#if



></#macro




>