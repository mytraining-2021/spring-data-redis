{{- define "spring-data-redis.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride -}}
{{- else -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}

{{- define "spring-data-redis.name" -}}
{{- .Chart.Name -}}
{{- end -}}

{{- define "spring-data-redis.labels" -}}
helm.sh/chart: {{ include "spring-data-redis.chart" . }}
app.kubernetes.io/name: {{ include "spring-data-redis.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}

{{- define "spring-data-redis.selectorLabels" -}}
app.kubernetes.io/name: {{ include "spring-data-redis.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}

{{- define "spring-data-redis.chart" -}}
{{ .Chart.Name }}-{{ .Chart.Version }}
{{- end -}}
