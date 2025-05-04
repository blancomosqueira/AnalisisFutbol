from django.db import models

from django.db import models

class Equipo(models.Model):
    nombre = models.CharField(max_length=100)

class Jugador(models.Model):
    nombre = models.CharField(max_length=100)
    equipo = models.ForeignKey(Equipo, on_delete=models.CASCADE)

class Evaluacion(models.Model):
    jugador = models.ForeignKey(Jugador, on_delete=models.CASCADE)
    accion = models.CharField(max_length=100)
    tipo = models.CharField(max_length=10)  # 'ofensiva' o 'defensiva'
    valoracion = models.IntegerField()
