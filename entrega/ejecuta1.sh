cd ./src/



#Compilamos

javac -d ./bin -sourcepath ./practica1/ ./practica1/grafo/*.java

javac -d ./bin -sourcepath ./practica1/ ./practica1/random/*.java

javac -d ./bin -sourcepath ./practica1/ ./practica1/*.java



cd ./bin



#Ejecuciones

java -cp . practica1.Main -s -p ../../pruebas/Grande.txt -a 0 > ../../resultados/Grande_Resultado_Random_normal.txt

java -cp . practica1.Main -s -p ../../pruebas/Grande.txt -a 1 > ../../resultados/Grande_Resultado_Random_mejor.txt



java -cp . practica1.Main -s -p ../../pruebas/Mediano.txt -a 0 > ../../resultados/Mediano_Resultado_Random_normal.txt

java -cp . practica1.Main -s -p ../../pruebas/Mediano.txt -a 1 > ../../resultados/Mediano_Resultado_Random_mejor.txt



java -cp . practica1.Main -s -p ../../pruebas/Pequeno.txt -a 0 > ../../resultados/Pequeno_Resultado_Random_normal.txt

java -cp . practica1.Main -s -p ../../pruebas/Pequeno.txt -a 1 > ../../resultados/Pequeno_Resultado_Random_mejor.txt

