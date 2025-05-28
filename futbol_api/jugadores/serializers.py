from rest_framework import serializers
from .models import Jugador, Equipo,Evaluacion

class EvaluacionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Evaluacion
        fields = ['jugador', 'accion', 'tipo', 'valoracion']

class JugadorSerializer(serializers.ModelSerializer):
    evaluaciones = serializers.SerializerMethodField()

    class Meta:
        model = Jugador
        fields = ['id', 'nombre', 'valoracion_media', 'equipo', 'evaluaciones']

    def get_evaluaciones(self, obj):
        evaluaciones = Evaluacion.objects.filter(jugador=obj)
        return {ev.accion: ev.valoracion for ev in evaluaciones}

# Serializer para Equipo
class EquipoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Equipo
        fields = '__all__'
