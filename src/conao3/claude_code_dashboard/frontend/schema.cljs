(ns conao3.claude-code-dashboard.frontend.schema
  (:require
   [schema.core :as s]))

(def ID s/Str)
(def ProjectId s/Str)
(def SessionId s/Str)
(def MessageId s/Str)
(def Timestamp s/Str)
(def Hiccup s/Any)

(def UrlPath
  {(s/optional-key :project-id) (s/maybe s/Str)
   (s/optional-key :session-id) (s/maybe s/Str)})

(def Project
  {:id ID
   :name s/Str
   :projectId ProjectId
   :hasSessions s/Bool})

(def Session
  {:id ID
   :projectId ProjectId
   :sessionId SessionId
   :createdAt Timestamp})

(def ContentBlock
  {:type (s/maybe s/Str)
   (s/optional-key :text) (s/maybe s/Str)
   (s/optional-key :thinking) (s/maybe s/Str)
   (s/optional-key :id) (s/maybe s/Str)
   (s/optional-key :name) (s/maybe s/Str)
   (s/optional-key :input) (s/maybe s/Str)
   (s/optional-key :tool_use_id) (s/maybe s/Str)
   (s/optional-key :content) (s/maybe s/Str)})

(def MessageContent
  {(s/optional-key :content) [ContentBlock]})

(def CompactMetadata
  {:trigger (s/maybe s/Str)
   :preTokens (s/maybe s/Int)})

(def Snapshot
  {(s/optional-key :messageId) (s/maybe s/Str)
   (s/optional-key :trackedFileBackups) (s/maybe s/Str)})

(def Message
  {:__typename s/Str
   (s/optional-key :id) (s/maybe ID)
   (s/optional-key :messageId) (s/maybe MessageId)
   (s/optional-key :rawMessage) (s/maybe s/Str)
   (s/optional-key :isSnapshotUpdate) (s/maybe s/Bool)
   (s/optional-key :snapshot) (s/maybe Snapshot)
   (s/optional-key :operation) (s/maybe s/Str)
   (s/optional-key :timestamp) (s/maybe Timestamp)
   (s/optional-key :content) s/Any
   (s/optional-key :queueSessionId) (s/maybe s/Str)
   (s/optional-key :subtype) (s/maybe s/Str)
   (s/optional-key :systemContent) (s/maybe s/Str)
   (s/optional-key :isMeta) (s/maybe s/Bool)
   (s/optional-key :level) (s/maybe s/Str)
   (s/optional-key :compactMetadata) (s/maybe CompactMetadata)
   (s/optional-key :summary) (s/maybe s/Str)
   (s/optional-key :leafUuid) (s/maybe s/Str)
   (s/optional-key :message) (s/maybe MessageContent)})

(def ToolResults {s/Str ContentBlock})

(def DisplayedToolUseIds #{s/Str})

(def NavItemProps
  {:icon s/Any
   :label s/Str
   :active s/Bool
   :collapsed s/Bool
   :on-click s/Any
   (s/optional-key :badge) (s/maybe s/Any)})

(def ProjectItemProps
  {:project Project
   :active s/Bool
   :collapsed s/Bool
   :on-click s/Any})

(def SessionItemProps
  {:session Session
   :active s/Bool
   :on-click s/Any})

(def ProjectsListProps
  {:on-select-project s/Any
   :collapsed s/Bool})

(def SidebarProps
  {:on-select-project s/Any})

(def SessionsListProps
  {:project-id (s/maybe ID)
   :on-select-session s/Any})

(def SessionsPanelProps
  {:project (s/maybe {:id ID :name (s/maybe s/Str)})
   :on-select-session s/Any})

(def CopyButtonProps
  {:text s/Str
   (s/optional-key :label) (s/maybe s/Str)
   (s/optional-key :class) (s/maybe s/Str)})

(def MarkdownProps
  {:children (s/maybe s/Str)
   (s/optional-key :class) (s/maybe s/Str)})

(def ToolResultBlockProps
  {:block ContentBlock})

(def ContentBlockProps
  {:block ContentBlock
   (s/optional-key :tool-results) (s/maybe ToolResults)})

(def MessageBubbleProps
  {:role (s/enum :user :assistant)
   (s/optional-key :icon) (s/maybe s/Any)
   (s/optional-key :icon-class) (s/maybe s/Str)
   (s/optional-key :time) (s/maybe s/Str)
   (s/optional-key :tool-count) (s/maybe s/Int)
   (s/optional-key :thinking?) (s/maybe s/Bool)})

(def AssistantMessageProps
  {:message Message
   (s/optional-key :tool-results) (s/maybe ToolResults)})

(def UserMessageProps
  {:message Message
   (s/optional-key :displayed-tool-use-ids) (s/maybe DisplayedToolUseIds)})

(def SystemMessageItemProps
  {:message Message})

(def SummaryMessageItemProps
  {:message Message})

(def FileHistorySnapshotMessageProps
  {:message Message})

(def QueueOperationMessageProps
  {:message Message})

(def UnknownMessageProps
  {:message Message})

(def BrokenMessageProps
  {:message Message})

(def SafeRenderMessageProps
  {:message Message
   (s/optional-key :tool-results) (s/maybe ToolResults)
   (s/optional-key :displayed-tool-use-ids) (s/maybe DisplayedToolUseIds)})
