from rest_framework import serializers
from .models import Jugador, Equipo,Evaluacion

# Serializer para Jugador
class JugadorSerializer(serializers.ModelSerializer):
    evaluaciones = serializers.SerializerMethodField()

    class Meta:
        model = Jugador
        fields = ['id', 'nombre', 'valoracion_media', 'equipo', 'evaluaciones']

    def get_evaluaciones(self, obj):
        evaluaciones = Evaluacion.objects.filter(jugador=obj)
        return [{"accion": e.accion, "tipo": e.tipo, "valoracion": e.valoracion} for e in evaluaciones]

# Serializer para Equipo
class EquipoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Equipo
        fields = '__all__'
