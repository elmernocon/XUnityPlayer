using System.Collections.Generic;
using UnityEngine;

public class Logger : MonoBehaviour
{
    #region Statics

    public static Logger Instance { get; private set; }

    #endregion
    
    #region Fields

    [Header("Logger Settings")]
    [SerializeField]
    private int _fontSize = 40;

    [SerializeField]
    private float _scrollbarThickness = 50.0f;

    [SerializeField]
    private float _scrollbarThumbMargin = 4.0f;

    [Header("Logs")]
    [SerializeField]
    private List<string> _logs = new List<string>();

    private bool _guiSkinSet;
    
    private Vector2 _scrollPosition;

    private Vector2Int _screenSize;

    private Rect _scrollViewRect;
    
    private GUIStyle _labelStyle;

    private GUILayoutOption _screenMinHeight;

    private GUILayoutOption _screenMinWidth;

    #endregion

    #region Methods

    public void AddLog(string log)
    {
        _logs.Add(log);
    }

    #endregion

    #region Unity Methods

    private void Awake()
    {
        Instance = this;
        
        new AndroidJavaClass("com.unity3d.xplayer.GameObjectListener")
           .CallStatic("setIndentSpaces", 4);
        
        _screenSize = new Vector2Int(Screen.width, Screen.height);
        _screenMinHeight = GUILayout.MinHeight(_screenSize.y);
        _screenMinWidth = GUILayout.MinWidth(_screenSize.x);
        
        _labelStyle = new GUIStyle
        {
            fontSize = _fontSize
        };
    }

    private void OnGUI()
    {
        if (!_guiSkinSet)
        {
            GUI.skin.horizontalScrollbar.fixedHeight = _scrollbarThickness;
            GUI.skin.verticalScrollbar.fixedWidth = _scrollbarThickness;
        
            GUI.skin.horizontalScrollbarThumb.fixedHeight = _scrollbarThickness - _scrollbarThumbMargin;
            GUI.skin.verticalScrollbarThumb.fixedWidth = _scrollbarThickness - _scrollbarThumbMargin;

            _guiSkinSet = true;
        }

        _scrollPosition = GUILayout.BeginScrollView(
            _scrollPosition,
            _screenMinHeight,
            _screenMinWidth);

        foreach (var log in _logs)
            GUILayout.Label(log, _labelStyle);

        GUILayout.EndScrollView();
    }

    #endregion
    
#if UNITY_EDITOR

    #region Debug

    private void Update()
    {
        if (Input.GetKeyDown(KeyCode.Space))
        {
            AddLog(System.Guid.NewGuid().ToString());
        }
    }

    #endregion

#endif
}
