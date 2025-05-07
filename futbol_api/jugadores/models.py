from django.db import models

from django.db import models

class Equipo(models.Model):
    nombre = models.CharField(max_length=100)
    def __str__(self):
        return self.nombre


class Jugador(models.Model):
    nombre = models.CharField(max_length=100)
    valoracion_media = models.FloatField(default=0.0)
    equipo = models.ForeignKey(Equipo, on_delete=models.CASCADE)
    def __str__(self):
        return self.nombre


class Evaluacion(models.Model):
    jugador = models.ForeignKey(Jugador, on_delete=models.CASCADE)
    accion = models.CharField(max_length=100)
    tipo = models.CharField(max_length=10)  # 'ofensiva' o 'defensiva'
    valoracion = models.IntegerField()
