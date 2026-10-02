package com.example.util

import com.example.model.AppLanguage

object AppStrings {
    fun get(key: String, language: AppLanguage): String {
        return when (language) {
            AppLanguage.ARABIC -> arabicStrings[key] ?: englishStrings[key] ?: key
            AppLanguage.FRENCH -> frenchStrings[key] ?: englishStrings[key] ?: key
            AppLanguage.ENGLISH -> englishStrings[key] ?: key
        }
    }

    private val arabicStrings = mapOf(
        "app_title" to "حماية الهاتف",
        "app_subtitle" to "تنظيف متطور ومكافح الفيروسات العالمي",
        "tab_dashboard" to "الرئيسية",
        "tab_antivirus" to "مكافح الفيروسات",
        "tab_cleaner" to "منظف المخلفات",
        "tab_optimizer" to "تسريع الهاتف",
        "tab_privacy" to "السيرفرات والخصوصية",
        "tab_settings" to "الإعدادات",
        "tab_history" to "السجل",

        // Security Status
        "status_secure" to "هاتفك محمي بالكامل",
        "status_secure_desc" to "قاعدة بيانات الفيروسات السحابية محدثة ولا توجد أي تهديدات نشطة",
        "status_warning" to "تنبيه: ملفات مشبوهة",
        "status_warning_desc" to "تم العثور على أذونات أو ملفات تحتاج مراجعتك",
        "status_danger" to "خطر: تهديدات نشطة!",
        "status_danger_desc" to "يرجى تنظيف التهديدات المكتشفة فوراً لحماية بياناتك",

        // Quick Actions
        "action_scan" to "فحص الفيروسات",
        "action_clean" to "تنظيف المخلفات",
        "action_boost" to "تسريع الذاكرة",
        "action_cool" to "تبريد المعالج",

        // Metrics
        "ram_usage" to "استهلاك الذاكرة (RAM)",
        "storage_usage" to "سعة التخزين المستخدمة",
        "cpu_temp" to "حرارة المعالج",
        "battery_health" to "حالة البطارية",
        "network_security" to "أمان الشبكة",
        "realtime_shield" to "الدرع الواقي المباشر",
        "active" to "نشط",
        "safe" to "آمن",
        "optimal" to "ممتاز",

        // Cloud Server & Intelligence
        "cloud_server_title" to "شبكة الحماية السحابية العالمية",
        "cloud_server_desc" to "الاتصال بسيرفرات التهديدات العالمية لتحديث قواعد الفيروسات ومراقبة الأمان",
        "sync_cloud_now" to "مزامنة السيرفرات السحابية",
        "syncing_cloud" to "جارٍ الاتصال بالسيرفر العالمي...",
        "cloud_synced_success" to "تم الاتصال وتحديث قواعد الحماية بنجاح!",
        "cloud_signatures" to "تواقيع الفيروسات السحابية",
        "server_latency" to "سرعة استجابة السيرفر",
        "cloud_region" to "منطقة السيرفر النشط",
        "database_version" to "إصدار قاعدة البيانات",

        // Antivirus Screen
        "deep_scan_title" to "الفحص الأمني العميق",
        "deep_scan_desc" to "فحص شامل للتطبيقات، ملفات التثبيت APK، والبرمجيات الخبيثة السحابية",
        "start_deep_scan" to "بدء الفحص الشامل الآن",
        "scanning_now" to "جارٍ فحص الهاتف وحمايته...",
        "test_malware_toggle" to "إدراج فحص تجريبي (EICAR)",
        "threats_detected" to "التهديدات المكتشفة",
        "no_threats_found" to "رائع! لم يتم العثور على أي فيروسات أو برمجيات خبيثة",
        "resolve_all" to "تطهير وحل جميع التهديدات",
        "whitelist" to "استثناء",
        "resolve" to "إزالة وحظر",

        // Cleaner Screen
        "cleaner_title" to "تنظيف الهاتف والمخلفات",
        "cleaner_desc" to "تفريغ ذاكرة التخزين المؤقت، الملفات المتبقية والملفات الزائدة",
        "clean_selected" to "تنظيف المخلفات المحددة",
        "clean_complete_title" to "تم تنظيف الهاتف بنجاح!",
        "clean_complete_desc" to "تم توفير مساحة إضافية وزيادة سرعة الهاتف",

        // Optimizer Screen
        "optimizer_title" to "تسريع الهاتف وتبريد المعالج",
        "boost_ram_button" to "تسريع الذاكرة العشوائية",
        "cool_cpu_button" to "تفعيل تبريد المعالج CPU",
        "ram_boosted_msg" to "تم تفريغ مساحة من الذاكرة بنجاح وتسريع النظام!",
        "cpu_cooled_msg" to "تم تخفيف الحمل عن المعالج وتبريد الجهاز بنجاح!",

        // Privacy
        "privacy_title" to "مستشار الأمان وأذونات التطبيقات",
        "privacy_desc" to "مراقبة التطبيقات التي تصل إلى الكاميرا، الميكروفون، والموقع",
        "open_settings" to "إدارة الأذونات",

        // Comprehensive Settings
        "settings_title" to "إعدادات الحماية والتطبيق",
        "settings_subtitle" to "تخصيص محرك الفيروسات، السيرفرات السحابية والخيارات المتقدمة",
        "settings_general" to "الإعدادات العامة والأمان",
        "settings_realtime" to "الدرع الواقي المباشر (Real-Time Shield)",
        "settings_realtime_desc" to "فحص تلقائي فوري لأي تطبيق جديد يتم تثبيته على الجهاز",
        "settings_cloud" to "الاتصال بشبكة الحماية السحابية",
        "settings_cloud_desc" to "مشاركة التهديدات المكتشفة ومطابقتها فورياً مع السيرفرات الدولية",
        "settings_safeweb" to "درع التصفح الآمن ومكافحة التصيد",
        "settings_safeweb_desc" to "حظر المواقع المزيفة والروابط المشبوهة تلقائياً",
        "settings_autoscan" to "الفحص الأمني التلقائي اليومي",
        "settings_autoscan_desc" to "إجراء فحص سريع في الخلفية عند توصيل الهاتف بالشاحن",
        "settings_smartjunk" to "تنبيه المخلفات الذكي",
        "settings_smartjunk_desc" to "إشعار المستخدم عندما تتجاوز مخلفات الذاكرة 500 ميجابايت",
        "settings_turboram" to "تسريع الرام الفائق (Turbo Boost)",
        "settings_turboram_desc" to "تنظيف قوي ومكثف لجميع العمليات غير الضرورية في الخلفية",
        "settings_heuristic" to "مستوى التحليل الاستدلالي (Heuristics)",
        "settings_heuristic_desc" to "درجة صرامة فحص السلوك البرمجي المشبوه",

        // Report & System Info
        "settings_report_title" to "تقرير الأمان الشامل للجهاز",
        "export_report_button" to "عرض وتصدير تقرير الأمان",
        "report_exported_msg" to "تم تجهيز التقرير الأمني الكامل بنجاح",
        "history_title" to "سجل عمليات الحماية والتنظيف",
        "empty_history" to "لا توجد عمليات مسجلة حتى الآن",
        "clear_history" to "مسح السجل",
        "language_select" to "لغة التطبيق (Language)",
        "auto_scan_label" to "الفحص التلقائي اليومي",
        "tab_ai" to "المستشار الذكي AI",
        "ai_advisor_title" to "المستشار الأمني الذكي (Gemini AI)",
        "ai_advisor_desc" to "تحليل سيبراني متقدم، كشف برمجيات التجسس، وتوصيات الأمان الفورية",
        "ai_input_placeholder" to "اسأل الذكاء الاصطناعي عن أمان جهازك، التطبيقات، أو الروابط...",
        "ai_send" to "إرسال",
        "ai_thinking" to "المستشار الذكي يحلل البيانات...",
        "about_app_title" to "حول التطبيق والمطور",
        "about_app_desc" to "معلومات التطبيق، الميزات الكاملة وهوية المطور",
        "developer_label" to "المطور الرئيسي",
        "developer_name" to "Ahmed Becetti",
        "developer_role" to "مهندس الأمن السيبراني وهندسة البرمجيات",
        "all_features_title" to "دليل مميزات الحماية والتنظيف الكاملة",
        "view_all_features" to "عرض جميع مميزات التطبيق بالتفصيل",
        "gemini_api_key_setting" to "مفتاح Gemini API (اختياري)",
        "gemini_api_key_desc" to "اربط مفتاحك الخاص من Google AI للحصول على استجابات سريعة ومخصصة",
        "protection_engine" to "محرك حماية الهواتف المطور 2026 - بواسطة Ahmed Becetti",
        "app_version" to "الإصدار 2.5 Pro - حماية سيبرانية شاملة",
        "privacy_policy_title" to "سياسة الخصوصية (Privacy Policy)",
        "privacy_policy_desc" to "معتمدة ومطابقة لسياسات Google Play ومطورة بواسطة Ahmed Becetti"
    )

    private val frenchStrings = mapOf(


        "app_title" to "Protection Téléphone",
        "app_subtitle" to "Nettoyeur Avancé & Antivirus Mondial",
        "tab_dashboard" to "Accueil",
        "tab_antivirus" to "Antivirus",
        "tab_cleaner" to "Nettoyeur",
        "tab_optimizer" to "Accélération",
        "tab_privacy" to "Serveurs & Privé",
        "tab_settings" to "Paramètres",
        "tab_history" to "Historique",

        // Security Status
        "status_secure" to "Votre appareil est protégé",
        "status_secure_desc" to "Bases virales cloud à jour. Aucune menace active détectée.",
        "status_warning" to "Avertissement de sécurité",
        "status_warning_desc" to "Fichiers ou autorisations suspectes à vérifier.",
        "status_danger" to "Risque Détecté !",
        "status_danger_desc" to "Nettoyez immédiatement les menaces détectées.",

        // Quick Actions
        "action_scan" to "Scan Antivirus",
        "action_clean" to "Nettoyer Déchets",
        "action_boost" to "Booster RAM",
        "action_cool" to "Refroidir CPU",

        // Metrics
        "ram_usage" to "Utilisation RAM",
        "storage_usage" to "Espace Stockage Utilisé",
        "cpu_temp" to "Température CPU",
        "battery_health" to "État Batterie",
        "network_security" to "Sécurité Réseau",
        "realtime_shield" to "Bouclier Temps Réel",
        "active" to "Actif",
        "safe" to "Sûr",
        "optimal" to "Optimal",

        // Cloud Server & Intelligence
        "cloud_server_title" to "Réseau Cloud Mondial de Cybersécurité",
        "cloud_server_desc" to "Connexion directe aux serveurs mondiaux de veille antivirus et synchronisation des signatures",
        "sync_cloud_now" to "Synchroniser avec les Serveurs Cloud",
        "syncing_cloud" to "Connexion au serveur mondial...",
        "cloud_synced_success" to "Serveur connecté et base virale synchronisée !",
        "cloud_signatures" to "Signatures Virales Cloud",
        "server_latency" to "Latence du Serveur",
        "cloud_region" to "Région du Serveur Actif",
        "database_version" to "Version Base Virale",

        // Antivirus Screen
        "deep_scan_title" to "Analyse Antivirus Approfondie",
        "deep_scan_desc" to "Analyse des applications, installateurs APK et scripts malveillants",
        "start_deep_scan" to "Lancer l'analyse complète",
        "scanning_now" to "Analyse en cours de l'appareil...",
        "test_malware_toggle" to "Inclure malware de test (EICAR)",
        "threats_detected" to "Menaces Détectées",
        "no_threats_found" to "Parfait ! Aucun virus ou logiciel espion trouvé",
        "resolve_all" to "Résoudre et désinfecter tout",
        "whitelist" to "Ignorer",
        "resolve" to "Nettoyer",

        // Cleaner Screen
        "cleaner_title" to "Nettoyage des Déchets & Cache",
        "cleaner_desc" to "Supprimez le cache système, fichiers résiduels et paquets inutiles",
        "clean_selected" to "Nettoyer les éléments sélectionnés",
        "clean_complete_title" to "Nettoyage réussi !",
        "clean_complete_desc" to "Espace libéré et performances améliorées.",

        // Optimizer Screen
        "optimizer_title" to "Accélération & Refroidissement",
        "boost_ram_button" to "Libérer la mémoire RAM",
        "cool_cpu_button" to "Refroidir le Processeur CPU",
        "ram_boosted_msg" to "Processus d'arrière-plan optimisés !",
        "cpu_cooled_msg" to "Température et charge processeur réduites !",

        // Privacy
        "privacy_title" to "Audit de Sécurité & Confidentialité",
        "privacy_desc" to "Applications accédant à la Caméra, Micro, Contacts et GPS",
        "open_settings" to "Gérer",

        // Settings
        "settings_title" to "Paramètres de Protection & Système",
        "settings_subtitle" to "Personnalisez le moteur antivirus, les serveurs cloud et options",
        "settings_general" to "Sécurité Générale & Bouclier",
        "settings_realtime" to "Bouclier Temps Réel (Real-Time)",
        "settings_realtime_desc" to "Analyse immédiate de chaque nouvelle application installée",
        "settings_cloud" to "Réseau de Détection Cloud Mondial",
        "settings_cloud_desc" to "Vérification en direct contre la base de données virale internationale",
        "settings_safeweb" to "Navigation Sécurisée & Anti-Phishing",
        "settings_safeweb_desc" to "Bloque les liens frauduleux et sites malveillants",
        "settings_autoscan" to "Analyse Automatique Quotidienne",
        "settings_autoscan_desc" to "Analyse rapide en tâche de fond quand l'appareil charge",
        "settings_smartjunk" to "Alerte de Déchets Intelligente",
        "settings_smartjunk_desc" to "Notification dès que les déchets dépassent 500 Mo",
        "settings_turboram" to "Mode Turbo RAM Booster",
        "settings_turboram_desc" to "Nettoyage agressif des processus en arrière-plan",
        "settings_heuristic" to "Niveau Heuristique d'Analyse",
        "settings_heuristic_desc" to "Sensibilité de la détection comportementale",

        // Report & System Info
        "settings_report_title" to "Rapport d'Audit de Sécurité Complet",
        "export_report_button" to "Afficher & Exporter le Rapport",
        "report_exported_msg" to "Rapport d'audit de sécurité généré avec succès",
        "history_title" to "Journal de Protection & Nettoyage",
        "empty_history" to "Aucun historique pour le moment",
        "clear_history" to "Effacer l'historique",
        "language_select" to "Langue de l'application (Language)",
        "auto_scan_label" to "Protection en temps réel active",
        "tab_ai" to "Conseiller IA",
        "ai_advisor_title" to "Conseiller Sécurité IA (Gemini IA)",
        "ai_advisor_desc" to "Analyse cybernétique, détection de spywares et recommandations de sécurité immédiates",
        "ai_input_placeholder" to "Interrogez l'IA sur la sécurité, les liens ou autorisations...",
        "ai_send" to "Envoyer",
        "ai_thinking" to "L'IA analyse vos paramètres de sécurité...",
        "about_app_title" to "À propos & Développeur",
        "about_app_desc" to "Spécifications de l'application et créateur",
        "developer_label" to "Développeur Principal",
        "developer_name" to "Ahmed Becetti",
        "developer_role" to "Ingénieur en Cybersécurité & Systèmes",
        "all_features_title" to "Toutes les Fonctionnalités Détaillées",
        "view_all_features" to "Voir Toutes les Fonctionnalités",
        "gemini_api_key_setting" to "Clé API Gemini (Optionnel)",
        "gemini_api_key_desc" to "Liez votre clé Google AI pour des réponses illimitées",
        "protection_engine" to "Moteur de Cybersécurité Mobile 2026 - par Ahmed Becetti",
        "app_version" to "Version 2.5 Pro - Protection Globale Active",
        "privacy_policy_title" to "Politique de Confidentialité",
        "privacy_policy_desc" to "Conforme aux règles de Google Play et développé par Ahmed Becetti"
    )

    private val englishStrings = mapOf(


        "app_title" to "Protection Téléphone",
        "app_subtitle" to "Advanced Phone Cleaner & Global Antivirus",
        "tab_dashboard" to "Home",
        "tab_antivirus" to "Antivirus",
        "tab_cleaner" to "Cleaner",
        "tab_optimizer" to "Speed Booster",
        "tab_privacy" to "Cloud & Privacy",
        "tab_settings" to "Settings",
        "tab_history" to "History",

        "status_secure" to "Your Phone is Fully Protected",
        "status_secure_desc" to "Cloud virus definitions are up to date. Zero active threats detected.",
        "status_warning" to "Security Warning Detected",
        "status_warning_desc" to "Suspicious files or risky permission combinations require your attention.",
        "status_danger" to "High Security Risk Detected!",
        "status_danger_desc" to "Clean and neutralize detected threats immediately to protect your privacy.",

        "action_scan" to "Antivirus Scan",
        "action_clean" to "Clean Junk",
        "action_boost" to "Boost RAM",
        "action_cool" to "Cool CPU",

        "ram_usage" to "RAM Memory Usage",
        "storage_usage" to "Storage Space Used",
        "cpu_temp" to "CPU Temperature",
        "battery_health" to "Battery Status",
        "network_security" to "Network Security",
        "realtime_shield" to "Real-time Shield",
        "active" to "Active",
        "safe" to "Secure",
        "optimal" to "Optimal",

        // Cloud Server & Intelligence
        "cloud_server_title" to "Global Cloud Threat Intelligence Network",
        "cloud_server_desc" to "Connected to global threat servers for real-time virus definitions updates and zero-day protection",
        "sync_cloud_now" to "Sync With Global Cloud Servers",
        "syncing_cloud" to "Connecting to Global Security Node...",
        "cloud_synced_success" to "Connected successfully! Security signatures updated.",
        "cloud_signatures" to "Cloud Virus Signatures",
        "server_latency" to "Server Latency",
        "cloud_region" to "Active Cloud Node",
        "database_version" to "Threat DB Version",

        "deep_scan_title" to "Deep Antivirus Scan",
        "deep_scan_desc" to "Thorough inspection of apps, APK packages, system integrity, and malware scripts",
        "start_deep_scan" to "Start Deep Scan Now",
        "scanning_now" to "Scanning & protecting your device...",
        "test_malware_toggle" to "Include Test Malware (EICAR)",
        "threats_detected" to "Threats Detected",
        "no_threats_found" to "Great! No viruses or malware detected on your phone",
        "resolve_all" to "Clean & Resolve All Threats",
        "whitelist" to "Whitelist",
        "resolve" to "Clean & Remove",

        "cleaner_title" to "Phone Junk & Cache Cleaner",
        "cleaner_desc" to "Clean system cache, leftover app data, obsolete APK files, and temp files",
        "clean_selected" to "Clean Selected Junk Files",
        "clean_complete_title" to "Cleaning Successful!",
        "clean_complete_desc" to "Storage space recovered and device speed enhanced.",

        "optimizer_title" to "RAM Booster & CPU Cooler",
        "boost_ram_button" to "Boost RAM Memory",
        "cool_cpu_button" to "Cool CPU Temperature",
        "ram_boosted_msg" to "Background memory released and system speed boosted!",
        "cpu_cooled_msg" to "CPU workload lowered and temperature cooled down!",

        "privacy_title" to "Privacy & Permissions Advisor",
        "privacy_desc" to "Audit apps accessing Camera, Microphone, GPS Location, Contacts, and SMS",
        "open_settings" to "Manage Permissions",

        // Comprehensive Settings
        "settings_title" to "Security & App Settings",
        "settings_subtitle" to "Configure antivirus engine, global cloud servers, and advanced tools",
        "settings_general" to "General Protection & Shields",
        "settings_realtime" to "Real-Time Protection Shield",
        "settings_realtime_desc" to "Automatically scans every newly installed app or APK in real time",
        "settings_cloud" to "Global Cloud Threat Network",
        "settings_cloud_desc" to "Live matching against millions of known virus hashes globally",
        "settings_safeweb" to "Safe Web & Phishing Shield",
        "settings_safeweb_desc" to "Block dangerous web connections and fraudulent phishing links",
        "settings_autoscan" to "Automatic Daily Background Scan",
        "settings_autoscan_desc" to "Runs a quiet background check while device is connected to charger",
        "settings_smartjunk" to "Smart Junk Space Reminder",
        "settings_smartjunk_desc" to "Alerts you whenever unnecessary cache exceeds 500 MB",
        "settings_turboram" to "Turbo RAM Boost Mode",
        "settings_turboram_desc" to "Aggressive memory trimming for intensive apps and gaming",
        "settings_heuristic" to "Heuristic Analysis Level",
        "settings_heuristic_desc" to "Strictness of behavioral pattern detection",

        // Report & System Info
        "settings_report_title" to "Device Security Audit Report",
        "export_report_button" to "View & Export Security Audit",
        "report_exported_msg" to "Comprehensive security audit report ready",
        "history_title" to "Protection & Cleaning History",
        "empty_history" to "No history recorded yet",
        "clear_history" to "Clear History",
        "language_select" to "Application Language",
        "auto_scan_label" to "Real-Time Protection Active",
        "tab_ai" to "AI Advisor",
        "ai_advisor_title" to "AI Security Advisor (Gemini AI)",
        "ai_advisor_desc" to "Advanced cyber intelligence, spyware detection, and instant security advice",
        "ai_input_placeholder" to "Ask AI about device security, suspicious links, or app permissions...",
        "ai_send" to "Send",
        "ai_thinking" to "AI is analyzing device parameters...",
        "about_app_title" to "About App & Developer",
        "about_app_desc" to "Application specifications, full features, and creator profile",
        "developer_label" to "Lead Developer",
        "developer_name" to "Ahmed Becetti",
        "developer_role" to "Lead Cyber Security & Systems Engineer",
        "all_features_title" to "Comprehensive Security & Cleaning Features",
        "view_all_features" to "View All Features Detailed",
        "gemini_api_key_setting" to "Gemini API Key (Optional)",
        "gemini_api_key_desc" to "Connect your Google AI key for unlimited instant cloud reasoning",
        "protection_engine" to "Cyber Security Engine 2026 - Developed by Ahmed Becetti",
        "app_version" to "Version 2.5 Pro - Global Protection Active",
        "privacy_policy_title" to "Privacy Policy",
        "privacy_policy_desc" to "Google Play compliant privacy policy maintained by Ahmed Becetti"
    )
}


